package com.ql2.myshop.ui.products.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ql2.myshop.base.BaseFragment
import com.ql2.myshop.databinding.FragmentProductDetailBinding
import com.ql2.myshop.domain.model.product.ProductModel
import com.ql2.myshop.ui.products.ProductFragment
import com.ql2.myshop.utils.AssetUtils
import com.ql2.myshop.utils.formatPrice
import dagger.hilt.android.AndroidEntryPoint

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
    }
    private fun bindData(productModel: ProductModel){
        AssetUtils.loadImageFromAssets(requireContext(), fileName = "dell_5450_2024.jpg", binding.ivProductTop)
        binding.proNameEditText.setText(productModel.productName)
        binding.proPriceEditText.setText(formatPrice(productModel.importPrice))
        binding.proQuantityEditText.setText(productModel.quantity.toString())
        binding.proDesEditText.setText(productModel.description)
    }
}