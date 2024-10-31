package com.ql2.myshop.ui.products

import androidx.lifecycle.viewModelScope
import com.ql2.myshop.base.BaseViewModel
import com.ql2.myshop.domain.model.product.UpdateProductByIdModel
import com.ql2.myshop.domain.usecase.product.AddProductUseCase
import com.ql2.myshop.domain.usecase.product.GetProductUseCase
import com.ql2.myshop.domain.usecase.product.SearchProductUseCase
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
    private val searchProductUseCase: SearchProductUseCase,
    private val updateProductByIdUseCase: UpdateProductByIdUseCase,
    private val addProductUseCase: AddProductUseCase
) : BaseViewModel() {

    private val _uiGetProductModel = MutableStateFlow(ProductUIModel())
    val uiGetProductModel = _uiGetProductModel.asStateFlow()

    private val _uiSearchProductModel = MutableStateFlow(ProductUIModel())
    val uiSearchProductModel = _uiSearchProductModel.asStateFlow()

    private val _uiUpdateProductModel = MutableStateFlow(UpdateProductUIModel())
    val uiUpdateProductModel = _uiUpdateProductModel.asStateFlow()

    private val _uiAddProductModel = MutableStateFlow(AddProductUIModel())
    val uiAddProductModel = _uiAddProductModel.asStateFlow()

    fun getListProducts() {
        viewModelScope.launch {
            getProductUseCase().collectAsState(_uiGetProductModel)
        }
    }

    fun searchProducts(cateId: Int, proName: String) {
        viewModelScope.launch {
            searchProductUseCase(cateId, proName).collectAsState(_uiSearchProductModel)
        }
    }

    fun updateProductById(proId: Int, importPrice: Float, quantity: Int, description: String,  proName: String) {
        viewModelScope.launch {
            updateProductByIdUseCase(proId, importPrice, quantity, description, proName)
                .collectAsState(_uiUpdateProductModel)
        }
    }
    fun addNewProduct(cateId: Int, importPrice: Float, quantity: Int,
                      description: String,  proName: String, proImage: String) {
        viewModelScope.launch {
            addProductUseCase(cateId, importPrice, quantity, description, proName, proImage)
                .collectAsState(_uiAddProductModel)
        }
    }
}