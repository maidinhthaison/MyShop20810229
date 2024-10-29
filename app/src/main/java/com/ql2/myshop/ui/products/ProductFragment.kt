package com.ql2.myshop.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.ql2.myshop.base.BaseFragment
import com.ql2.myshop.databinding.FragmentProductBinding
import com.ql2.myshop.domain.model.product.ProductModel
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
                Timber.d(">>>${it.data}")
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
        val colors = arrayOf("Red","Green","Blue")
        ///val arrayAdapter = ArrayAdapter<String>(context = requireContext(), android.R.layout.simple_spinner_dropdown_item, colors.toList())
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, colors.toList())
        binding.spinnerCate.adapter= arrayAdapter
        binding.spinnerCate.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Timber.d(">>${colors[position]}")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

    }

    private fun gotoDetailScreen(productModel: ProductModel) {
        val bundle = Bundle().apply {
            this.putSerializable(PRODUCT_ITEM_MODEL, productModel)
        }
        Timber.d(">>>${productModel.productId}")
        //findNavController().navigate(R.id.action_homeFragment_to_fragmentMovieDetail, bundle)
    }

    companion object{
        const val PRODUCT_ITEM_MODEL = "ProductItemModel"
    }
}