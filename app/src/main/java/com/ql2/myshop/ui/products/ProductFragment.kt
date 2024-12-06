package com.ql2.myshop.ui.products

import android.annotation.SuppressLint
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
import com.ql2.myshop.data.api.request.GetAllProductRequestDTO
import com.ql2.myshop.data.api.request.GetProductByCateAndNameRequestDTO
import com.ql2.myshop.data.api.request.GetProductByCateRequestDTO
import com.ql2.myshop.data.api.request.GetProductByNameRequestDTO
import com.ql2.myshop.databinding.FragmentProductBinding
import com.ql2.myshop.domain.local.SettingApp
import com.ql2.myshop.domain.model.product.ProductModel
import com.ql2.myshop.ui.category.CategoryViewModel
import com.ql2.myshop.ui.products.adapter.ListProductUIEvent
import com.ql2.myshop.ui.products.adapter.ProductAdapter
import com.ql2.myshop.utils.LIMIT_DEFAULT
import com.ql2.myshop.utils.SORT
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

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
    private var offset: Int? = 0
    private var limit: Int? = 0

    @Inject
    lateinit var settingApp: SettingApp

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        limit = settingApp.getSetting()?.limit ?: LIMIT_DEFAULT
        productAdapter = ProductAdapter(context = requireContext())
        binding.rvProducts.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }
        paging()

        productAdapter.onClicked = {
            when (it) {
                is ListProductUIEvent.OnItemClicked -> {
                    gotoDetailScreen(it.productItem)
                }

                else -> {}
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
                arrayAdapter.clear()
                arrayAdapter.add(getString(R.string.all))
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
                            cateId = if (position == 0) {
                                0
                            } else {
                                it.data[position - 1].cateId
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }
                    }
            }
        }
        //Search
        binding.buttonSearch.setOnClickListener {
            offset = 0
            paging()
        }
        /**
         * Add Product
         */
        binding.btnAddProduct.setOnClickListener {
            findNavController().navigate(
                R.id.addProductBottomSheetFragment
            )
        }
        /**
         * Add Category
         */
        binding.btnAddCate.setOnClickListener {
            findNavController().navigate(
                R.id.addCategoryBottomSheetFragment
            )
        }
        /**
         * Pre Next Button Click
         */
        binding.ivPre.setOnClickListener {
            offset = offset?.minus(limit!!)
            if (offset!! < 0) {
                offset = 0
                binding.ivPre.isEnabled = false
            } else {
                binding.ivPre.isEnabled = true
                paging()
            }

        }
        binding.ivNext.setOnClickListener {
            offset = offset?.plus(limit!!)
            paging()
        }
        /**
         * Sort
         */
        val settingModel = settingApp.getSetting()
        if (settingModel != null) {
            binding.tvSortStatus.text = String.format(getString(R.string.sort_status), settingModel.sort)
            binding.sortSwitch.isChecked = settingModel.sort != SORT.DESC
        }else{
            binding.tvSortStatus.text = String.format(getString(R.string.sort_status), SORT.ASC)
            binding.sortSwitch.isChecked = true
        }
        binding.sortSwitch.setOnCheckedChangeListener { _, isChecked ->
            run {
                when(isChecked){
                    true -> {
                        binding.tvSortStatus.text = String.format(getString(R.string.sort_status), SORT.ASC)
                        val listData = productAdapter.currentList.sortedWith(compareBy { it.salePrice })
                        productAdapter.submitList(listData)
                    }else -> {
                        binding.tvSortStatus.text = String.format(getString(R.string.sort_status), SORT.DESC)
                        val listData = productAdapter.currentList.sortedWith(compareByDescending { it.salePrice })
                        productAdapter.submitList(listData)
                    }
                }
            }

        }
    }

    override fun onPause() {
        super.onPause()
        binding.sortSwitch.setOnCheckedChangeListener(null)
    }
    @SuppressLint("SetTextI18n")
    private fun paging() {
        Timber.d(">>>limit :$limit - Offset: $offset")
        val proName = binding.editTextProductName.text.toString()
        with(productViewModel) {
            if (cateId == 0 && proName.isEmpty()) {
                getListProducts(
                    getAllProductRequestDTO =
                    GetAllProductRequestDTO(limit = limit, offset = offset)
                )
            } else if (cateId != 0 && proName.isEmpty()) {
                getListProductsByCate(
                    getProductByCateRequestDTO =
                    GetProductByCateRequestDTO(cateId = cateId, limit = limit, offset = offset)
                )
            } else if (cateId == 0 && proName.isNotEmpty()) {
                getListProductsByName(
                    getProductByNameRequestDTO =
                    GetProductByNameRequestDTO(proName = proName, limit = limit, offset = offset)
                )
            } else if (cateId != 0 && proName.isNotEmpty()) {
                getListProductsByCateAndName(
                    getProductByCateAndNameRequestDTO =
                    GetProductByCateAndNameRequestDTO(
                        cateId = cateId, proName = proName,
                        limit = limit, offset = offset
                    )
                )
            }

        }
        productViewModel.uiGetProductModel.collectWhenStarted { it ->
            binding.loadingProgress.isVisible = it.isLoading
            if (it.data != null) {
                binding.tvPaging.text = String.format(
                    getString(R.string.label_paging),
                    "${offset?.div(limit!!)?.plus(1)}"
                )
                if (it.data.size < limit!!) binding.ivNext.isEnabled = false
                else {
                    binding.ivNext.isEnabled = true
                    binding.ivPre.isEnabled = true
                }
                //productAdapter.submitList(it.data.toList())
                // sort price
                when(binding.sortSwitch.isChecked){
                    true -> {
                        val listData = it.data.sortedWith(compareBy { it.salePrice })
                        productAdapter.submitList(listData)
                    }
                    else -> {
                        val listData = it.data.sortedWith(compareByDescending { it.salePrice })
                        productAdapter.submitList(listData)
                    }
                }

            }

        }
    }

    private fun gotoDetailScreen(productModel: ProductModel) {
        val bundle = Bundle().apply {
            this.putSerializable(PRODUCT_ITEM_MODEL, productModel)
        }

        findNavController().navigate(
            R.id.action_navigation_product_to_productDetailFragment,
            bundle
        )
    }

    companion object {
        const val PRODUCT_ITEM_MODEL = "ProductItemModel"
    }
}