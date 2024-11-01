package com.ql2.myshop.ui.orders.bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ql2.myshop.base.BaseBottomSheetDialogFragment

import com.ql2.myshop.databinding.FragmentOrderDetailBinding
import com.ql2.myshop.domain.model.category.CategoryModel
import com.ql2.myshop.ui.category.CategoryViewModel
import com.ql2.myshop.ui.products.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailFragment :
    BaseBottomSheetDialogFragment<FragmentOrderDetailBinding>() {

    private val productViewModel by viewModels<ProductViewModel>()

    private val categoryViewModel by viewModels<CategoryViewModel>()
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private lateinit var categoryModel: CategoryModel

    private var flag : Boolean = true

    override fun initBindingObject(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentOrderDetailBinding {
        return FragmentOrderDetailBinding.inflate(inflater, container, false)
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
        /*arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item)
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
            val proPrice = binding.proPriceEditText.text.toString().toFloat()
            val proQuantity = binding.proQuantityEditText.text.toString().toInt()
            val proDes = binding.proDesEditText.text.toString()
            val proImage = "asus_vivobook14_0.png@@asus_vivobook14_1.png@@asus_vivobook14_2.png"
            with(productViewModel) {
                cateId?.let { it1 -> addNewProduct(it1, proPrice, proQuantity, proDes, proName, proImage) }
            }
        }

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

        }*/
    }

}