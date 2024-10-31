package com.ql2.myshop.ui.products.detail

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ql2.myshop.R
import com.ql2.myshop.base.BaseFragment
import com.ql2.myshop.base.collectLatestWhenOwnerStarted
import com.ql2.myshop.databinding.FragmentProductDetailBinding
import com.ql2.myshop.domain.model.product.ProductModel
import com.ql2.myshop.ui.products.ProductFragment
import com.ql2.myshop.ui.products.ProductViewModel
import com.ql2.myshop.utils.AppDialog
import com.ql2.myshop.utils.AssetUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class ProductDetailFragment :
    BaseFragment<FragmentProductDetailBinding>() {
    override fun initBindingObject(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentProductDetailBinding {
        return FragmentProductDetailBinding.inflate(inflater, container, false)
    }

    private val productViewModel by viewModels<ProductViewModel>()

    private var flag : Boolean = true

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productItem: ProductModel? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getSerializable(
                    ProductFragment.PRODUCT_ITEM_MODEL,
                    ProductModel::class.java
                )
            } else {
                arguments?.getSerializable(ProductFragment.PRODUCT_ITEM_MODEL) as ProductModel

            }
        productItem?.let { bindData(it) }

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        /**
         * Update product
         */
        binding.buttonSave.setOnClickListener {
            val proName = binding.proNameEditText.text.toString()
            val proPrice = binding.proPriceEditText.text.toString().toFloat()
            val proQuantity = binding.proQuantityEditText.text.toString().toInt()
            val proDes = binding.proDesEditText.text.toString()
            val proId = productItem?.productId ?: 0
            with(productViewModel) {
                updateProductById(proId, proPrice, proQuantity, proDes, proName)
            }
        }

        productViewModel.uiUpdateProductModel.collectLatestWhenStarted {
            binding.loadingProgress.isVisible = it.isLoading
            if (flag) {
                val responseModel = it.data
                if (responseModel?.info !="" && responseModel?.affectedRows == 1){

                    AppDialog.displayErrorMessage(
                        requireContext(), R.string.dialog_update_product_title,
                        R.string.dialog_update_product_message,
                        R.string.ok
                    ) { _, _ ->
                        Timber.d(">>>>>>>111111")
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
    private fun bindData(productModel: ProductModel){
        AssetUtils.loadImageFromAssets(requireContext(), fileName = "dell_5450_2024.jpg", binding.ivProductTop)
        binding.proNameEditText.setText(productModel.productName)
        binding.proPriceEditText.setText(productModel.importPrice.toString())
        binding.proQuantityEditText.setText(productModel.quantity.toString())
        binding.proDesEditText.setText(productModel.description)


    }
}