package com.ql2.myshop.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.ql2.myshop.R
import com.ql2.myshop.base.BaseFragment
import com.ql2.myshop.databinding.FragmentProductBinding
import com.ql2.myshop.domain.model.product.ProductModel
import com.ql2.myshop.ui.category.CategoryViewModel
import com.ql2.myshop.ui.products.adapter.ListProductUIEvent
import com.ql2.myshop.ui.products.adapter.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProductFragment :
    BaseFragment<FragmentProductBinding>() {
    override fun initBindingObject(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentProductBinding {
        return FragmentProductBinding.inflate(inflater, container, false)
    }
    private val productViewModel by viewModels<ProductViewModel>()
    private lateinit var productAdapter: ProductAdapter

    private val categoryViewModel by viewModels<CategoryViewModel>()
    private lateinit var arrayAdapter: ArrayAdapter<String>

    private var cateId: Int? = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productAdapter =  ProductAdapter(context = requireContext())
        binding.rvMovies.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }

        with(productViewModel) {
            getListProducts()
        }

        productViewModel.uiGetProductModel.collectWhenStarted {
            binding.loadingProgress.isVisible = it.isLoading
            if (it.data != null) {
                productAdapter.submitList(it.data.toList())
            }
        }


        productAdapter.onClicked = {
            when (it) {
                is ListProductUIEvent.OnItemClicked -> {
                    gotoDetailScreen(it.productItem)
                }
            }
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
        //Search
        binding.buttonSearch.setOnClickListener {

            val proName = binding.editTextProductName.text.toString()
            with(productViewModel) {
                searchProducts(cateId ?: 0, proName)

            }
            productViewModel.uiSearchProductModel.collectWhenStarted {
                binding.loadingProgress.isVisible = it.isLoading
                if (it.data != null) {

                    productAdapter.submitList(it.data.toList())
                }

            }

        }
        /**
         * Add Product
         */
        binding.ivAddProduct.setOnClickListener {
            findNavController().navigate(
                R.id.addProductBottomSheetFragment
            )
        }
    }


    private fun gotoDetailScreen(productModel: ProductModel) {
        val bundle = Bundle().apply {
            this.putSerializable(PRODUCT_ITEM_MODEL, productModel)
        }

        findNavController().navigate(R.id.action_navigation_product_to_productDetailFragment, bundle)
    }

    companion object{
        const val PRODUCT_ITEM_MODEL = "ProductItemModel"
    }
}