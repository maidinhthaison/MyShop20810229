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
import com.ql2.myshop.ui.products.detail.adapter.ImageSlideAdapter
import com.ql2.myshop.utils.AppDialog
import com.ql2.myshop.utils.formatIntToString
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

    private val productViewModel by viewModels<ProductViewModel>()

    private var flag : Boolean = true

    private lateinit var viewPagerAdapter: ImageSlideAdapter

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
            val proPrice = binding.proPriceEditText.text.toString()
            val proQuantity = binding.proQuantityEditText.text.toString()
            val proDes = binding.proDesEditText.text.toString()
            val proId = productItem?.productId ?: 0
            with(productViewModel) {
                updateProductById(proId, proPrice.toInt(), proQuantity.toInt(), proDes, proName)
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
                        flag = false
                    }

                }
            }else{
                flag = true
            }
        }
        binding.buttonClear.setOnClickListener {
            findNavController().popBackStack()

        }
    }
    private fun bindData(productModel: ProductModel){

        viewPagerAdapter = ImageSlideAdapter(requireContext(),productModel.getImages())
        binding.viewpager.adapter = viewPagerAdapter
        binding.indicator.setViewPager(binding.viewpager)

        binding.proNameEditText.setText(productModel.productName)
        binding.proPriceEditText.setText(formatIntToString(productModel.salePrice))
        binding.proQuantityEditText.setText(productModel.quantity.toString())
        binding.proDesEditText.setText(productModel.description)


    }
}