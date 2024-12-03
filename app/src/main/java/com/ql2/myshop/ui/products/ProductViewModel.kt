package com.ql2.myshop.ui.products

import androidx.lifecycle.viewModelScope
import com.ql2.myshop.base.BaseViewModel
import com.ql2.myshop.data.api.request.GetAllProductRequestDTO
import com.ql2.myshop.data.api.request.GetProductByCateAndNameRequestDTO
import com.ql2.myshop.data.api.request.GetProductByCateRequestDTO
import com.ql2.myshop.data.api.request.GetProductByNameRequestDTO
import com.ql2.myshop.domain.usecase.product.AddProductUseCase
import com.ql2.myshop.domain.usecase.product.GetProductByCateAndNameUseCase
import com.ql2.myshop.domain.usecase.product.GetProductByCateUseCase
import com.ql2.myshop.domain.usecase.product.GetProductByNameUseCase
import com.ql2.myshop.domain.usecase.product.GetProductUseCase
import com.ql2.myshop.domain.usecase.product.UpdateProductByIdUseCase
import com.ql2.myshop.ui.products.bottomsheet.AddProductUIModel
import com.ql2.myshop.ui.products.detail.UpdateProductUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val getProductByCateUseCase: GetProductByCateUseCase,
    private val getProductByNameUseCase: GetProductByNameUseCase,
    private val getProductByCateAndNameUseCase: GetProductByCateAndNameUseCase,
    private val updateProductByIdUseCase: UpdateProductByIdUseCase,
    private val addProductUseCase: AddProductUseCase
) : BaseViewModel() {

    private val _uiGetProductModel = MutableStateFlow(ProductUIModel())
    val uiGetProductModel = _uiGetProductModel.asStateFlow()

    private val _uiUpdateProductModel = MutableStateFlow(UpdateProductUIModel())
    val uiUpdateProductModel = _uiUpdateProductModel.asStateFlow()

    private val _uiAddProductModel = MutableStateFlow(AddProductUIModel())
    val uiAddProductModel = _uiAddProductModel.asStateFlow()

    fun getListProducts(getAllProductRequestDTO: GetAllProductRequestDTO) {
        viewModelScope.launch {
            getProductUseCase(getAllProductRequestDTO)
                .collectAsState(_uiGetProductModel)
        }
    }

    fun getListProductsByCate(getProductByCateRequestDTO: GetProductByCateRequestDTO) {
        viewModelScope.launch {
            getProductByCateUseCase(getProductByCateRequestDTO)
                .collectAsState(_uiGetProductModel)
        }
    }

    fun getListProductsByName(getProductByNameRequestDTO: GetProductByNameRequestDTO)  {
        viewModelScope.launch {
            getProductByNameUseCase(getProductByNameRequestDTO)
                .collectAsState(_uiGetProductModel)
        }
    }

    fun getListProductsByCateAndName(getProductByCateAndNameRequestDTO: GetProductByCateAndNameRequestDTO) {
        viewModelScope.launch {
            getProductByCateAndNameUseCase(getProductByCateAndNameRequestDTO)
                .collectAsState(_uiGetProductModel)
        }
    }


    fun updateProductById(proId: Int, salePrice: Int, quantity: Int, description: String,  proName: String) {

        viewModelScope.launch {
            updateProductByIdUseCase(proId, salePrice, quantity, description, proName)
                .collectAsState(_uiUpdateProductModel)
        }
    }
    fun addNewProduct(cateId: Int, importPrice: Int, salePrice: Int, quantity: Int,
                      description: String,  proName: String, proImage: String) {
        viewModelScope.launch {
            addProductUseCase(cateId, importPrice, salePrice, quantity, description, proName, proImage)
                .collectAsState(_uiAddProductModel)
        }
    }
}