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
import com.ql2.myshop.R
import com.ql2.myshop.base.BaseFragment
import com.ql2.myshop.databinding.FragmentProductDetailBinding
import com.ql2.myshop.domain.model.product.ProductModel
import com.ql2.myshop.ui.products.ProductFragment
import com.ql2.myshop.ui.products.ProductViewModel
import com.ql2.myshop.utils.AppDialog
import com.ql2.myshop.utils.AssetUtils
import com.ql2.myshop.utils.formatPrice
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

        binding.buttonSave.setOnClickListener {
            val proName = binding.proNameEditText.text.toString()
            val proPrice = binding.proPriceEditText.text.toString().toFloat()
            val proQuantity = binding.proQuantityEditText.text.toString().toInt()
            val proDes = binding.proDesEditText.text.toString()
            val proId = productItem?.productId ?: 0
            with(productViewModel) {
                updateProductById(proId, proPrice, proQuantity, proDes, proName)
            }
            productViewModel.uiUpdateProductModel.collectWhenStarted {
                binding.loadingProgress.isVisible = it.isLoading
                val responseModel = it.data
                if (responseModel != null) {
                    if (responseModel.info!="" && responseModel.affectedRows == 1){
                        Timber.d(">>>>>>>")
                        AppDialog.displayErrorMessage(
                            requireActivity(), R.string.dialog_update_product_title,
                            R.string.dialog_update_product_message,
                            R.string.ok
                        ) { _, _ -> }
                    }
                }
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