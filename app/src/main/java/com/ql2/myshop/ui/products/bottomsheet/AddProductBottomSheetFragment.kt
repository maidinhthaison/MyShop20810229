package com.ql2.myshop.ui.products.bottomsheet

import android.R.attr
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ql2.myshop.R
import com.ql2.myshop.base.BaseBottomSheetDialogFragment
import com.ql2.myshop.databinding.FragmentAddProductBinding
import com.ql2.myshop.domain.model.category.CategoryModel
import com.ql2.myshop.ui.category.CategoryViewModel
import com.ql2.myshop.ui.products.ProductViewModel
import com.ql2.myshop.utils.AppDialog
import com.ql2.myshop.utils.FileUtils
import com.ql2.myshop.utils.StringExt.CHAR_SPLIT
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class AddProductBottomSheetFragment :
    BaseBottomSheetDialogFragment<FragmentAddProductBinding>() {

    private val productViewModel by viewModels<ProductViewModel>()

    private val categoryViewModel by viewModels<CategoryViewModel>()
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private lateinit var categoryModel: CategoryModel

    private var flag : Boolean = true

    @Inject lateinit var filesUtils: FileUtils

    private var cateId: Int? = 0

    override fun initBindingObject(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentAddProductBinding {
        return FragmentAddProductBinding.inflate(inflater, container, false)
    }

    override fun isExpandDialog(): Boolean {
        return true
    }

    override fun weightOfHeight(): Float? {
        return 0.8f
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        //val offsetFromTop = 200
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isFitToContents = true
            //expandedOffset = offsetFromTop
            state = BottomSheetBehavior.STATE_EXPANDED
        }

        /**
         * Spinner
         */
        arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCate.adapter = arrayAdapter
        with(categoryViewModel) {
            getAllCategories()
        }
        categoryViewModel.uiGetCategoryModel.collectWhenStarted { it ->
            if (it.data != null) {
                arrayAdapter.clear()
                arrayAdapter.addAll(it.data.map { it.cateName })
                arrayAdapter.notifyDataSetChanged()
                categoryModel = it.data[0]
                binding.spinnerCate.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            cateId = it.data[position].cateId

                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }
                    }
            }
        }
        binding.buttonSave.setOnClickListener {
            if(isValidate()){
                val proName = binding.proNameEditText.text.toString()
                val proImportPrice = binding.proPriceEditText.text.toString()
                val proSalePrice = binding.proSalePriceEditText.text.toString()
                val proQuantity = binding.proQuantityEditText.text.toString()
                val proDes = binding.proDesEditText.text.toString()
                val proImage = binding.proImagesEditText.text.toString().trim().dropLast(2)
                with(productViewModel) {
                    cateId?.let { it1 ->
                        addNewProduct(
                            it1, proImportPrice.toInt(), proSalePrice.toInt(),
                            proQuantity.toInt(), proDes, proName, proImage)
                    }
                }
            }

        }
        binding.ivCloseBottomSheet.setOnClickListener { this.dismiss() }

        productViewModel.uiAddProductModel.collectWhenStarted  {
            binding.loadingProgress.isVisible = it.isLoading

            if (flag) {
                val responseModel = it.data
                if (responseModel != null){

                    AppDialog.displayErrorMessage(
                        requireContext(), R.string.dialog_add_product_title,
                        R.string.dialog_add_product_message,
                        R.string.ok
                    ) { _, _ ->
                        flag = false
                    }

                }
            }else{
                flag = true
            }

        }
        binding.buttonClear.setOnClickListener {
            binding.proNameEditText.setText("")
            binding.proPriceEditText.setText("")
            binding.proQuantityEditText.setText("")
            binding.proDesEditText.setText("")
            binding.proImagesEditText.setText("")
        }

        binding.buttonBrowseImages.setOnClickListener { imageChooser() }

    }
    private fun imageChooser() {
        val i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)

        launchImagePicker.launch(i)
    }
    private var launchImagePicker
            : ActivityResultLauncher<Intent> = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult -> if (result.resultCode == Activity.RESULT_OK) {
            val response = result.data
            if (response != null){
                val selectedImageUri = response.data
                val fileRealPath = filesUtils.getRealPathFromURI(requireContext(), selectedImageUri!!)
                val file = File(fileRealPath)
                val fileName : String = file.name
                Timber.d("filePath : $fileName")
                val s = binding.proImagesEditText.text.toString().trim()
                if(s != ""){
                    val count = s.count { it == '@' }
                    if(count != 6){
                        val str = s.plus(fileName.plus(CHAR_SPLIT))
                        binding.proImagesEditText.setText(str)

                    }else{
                        AppDialog.displayErrorMessage(
                            requireContext(), R.string.dialog_select_image_title,
                            R.string.dialog_select_image_message,
                            R.string.ok
                        ) { _, _ ->
                            flag = false
                        }

                    }
                }else{
                    binding.proImagesEditText.setText(fileName.plus(CHAR_SPLIT))
                }

            }

        }
    }
    /**
     * Validation
     */
    private fun isValidate(): Boolean =
        validateProductName() && validateImportPrice() && validateSalePrice()
                && validateQuantity() && validateDescription() && validateImages()


    private fun setupListeners() {
        binding.proNameEditText.addTextChangedListener(TextFieldValidation(binding.proNameEditText))
        binding.proPriceEditText.addTextChangedListener(TextFieldValidation(binding.proPriceEditText))
        binding.proQuantityEditText.addTextChangedListener(TextFieldValidation(binding.proQuantityEditText))
        binding.proDesEditText.addTextChangedListener(TextFieldValidation(binding.proDesEditText))
        binding.proSalePriceEditText.addTextChangedListener(TextFieldValidation(binding.proSalePriceEditText))
        binding.proImagesEditText.addTextChangedListener(TextFieldValidation(binding.proImagesEditText))
    }

    /**
     * applying text watcher on each text field
     */
    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // checking ids of each text field and applying functions accordingly.
            when (view.id) {
                R.id.proNameEditText -> {
                    validateProductName()
                }

                R.id.proPriceEditText -> {
                    validateSalePrice()
                }

                R.id.proQuantityEditText -> {
                    validateQuantity()
                }

                R.id.proQuantityEditText -> {
                    validateDescription()
                }

            }
        }
    }

    /**
     *field must not be empty
     */
    private fun validateProductName(): Boolean {
        return if (binding.proNameEditText.text.toString().trim().isEmpty()) {
            binding.tilProName.error = "Required Field!"
            binding.proNameEditText.requestFocus()
            false
        } else {
            binding.tilProName.isErrorEnabled = false
            true
        }
    }

    /**
     * field must not be empty
     */
    private fun validateSalePrice(): Boolean {

        return if (binding.proPriceEditText.text.toString().trim().isEmpty()) {
            binding.tilProPrice.error = "Required Field!"
            binding.proPriceEditText.requestFocus()
            false
        } else {
            binding.tilProPrice.isErrorEnabled = false
            true
        }
    }
    /**
     *field must not be empty
     */
    private fun validateQuantity(): Boolean {

        return if (binding.proQuantityEditText.text.toString().trim().isEmpty()) {
            binding.tilProQuantity.error = "Required Field!"
            binding.proQuantityEditText.requestFocus()
            false
        } else {
            binding.tilProQuantity.isErrorEnabled = false
            true
        }
    }
    /**
     *field must not be empty
     */
    private fun validateDescription(): Boolean {

        return if (binding.proDesEditText.text.toString().trim().isEmpty()) {
            binding.tilProDes.error = "Required Field!"
            binding.proDesEditText.requestFocus()
            false
        } else {
            binding.tilProDes.isErrorEnabled = false
            true
        }
    }
    /**
     *field must not be empty
     */
    private fun validateImages(): Boolean {
        return if (binding.proImagesEditText.text.toString().trim().isEmpty()) {
            binding.tilProImages.error = "Required Field!"
            binding.proImagesEditText.requestFocus()
            false
        } else {
            binding.tilProImages.isErrorEnabled = false
            true
        }
    }
    /**
     *field must not be empty
     */
    private fun validateImportPrice(): Boolean {
        return if (binding.proPriceEditText.text.toString().trim().isEmpty()) {
            binding.tilProPrice.error = "Required Field!"
            binding.proPriceEditText.requestFocus()
            false
        } else {
            binding.tilProPrice.isErrorEnabled = false
            true
        }
    }

}