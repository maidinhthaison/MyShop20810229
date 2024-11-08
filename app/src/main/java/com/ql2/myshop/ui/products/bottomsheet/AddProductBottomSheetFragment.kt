package com.ql2.myshop.ui.products.bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ql2.myshop.R
import com.ql2.myshop.base.BaseBottomSheetDialogFragment
import com.ql2.myshop.base.collectWhenOwnerStarted
import com.ql2.myshop.databinding.FragmentAddProductBinding
import com.ql2.myshop.domain.model.category.CategoryModel
import com.ql2.myshop.ui.category.CategoryViewModel
import com.ql2.myshop.ui.products.ProductViewModel
import com.ql2.myshop.utils.AppDialog
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class AddProductBottomSheetFragment :
    BaseBottomSheetDialogFragment<FragmentAddProductBinding>() {

    private val productViewModel by viewModels<ProductViewModel>()

    private val categoryViewModel by viewModels<CategoryViewModel>()
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private lateinit var categoryModel: CategoryModel

    private var flag : Boolean = true

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
                //val listBloodTypes = listCategory.map { it.cateName }
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

                            categoryModel = it.data[position]

                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }
                    }
            }
        }
        binding.buttonSave.setOnClickListener {
            val cateId = categoryModel.cateId
            val proName = binding.proNameEditText.text.toString()
            val proImportPrice = binding.proPriceEditText.text.toString()
            val proSalePrice = binding.proSalePriceEditText.text.toString()
            val proQuantity = binding.proQuantityEditText.text.toString()
            val proDes = binding.proDesEditText.text.toString()
            val proImage = "asus_vivobook14_0.png@@asus_vivobook14_1.png@@asus_vivobook14_2.png"
            with(productViewModel) {
                if (cateId != null) {
                    addNewProduct(cateId, proImportPrice.toInt(), proSalePrice.toInt(),
                        proQuantity.toInt(), proDes, proName, proImage)
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

        }
    }

}