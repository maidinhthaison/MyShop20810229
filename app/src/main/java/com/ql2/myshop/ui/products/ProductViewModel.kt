package com.ql2.myshop.ui.products

import androidx.lifecycle.viewModelScope
import com.ql2.myshop.base.BaseViewModel
import com.ql2.myshop.domain.usecase.product.GetProductUseCase
import com.ql2.myshop.domain.usecase.product.SearchProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val searchProductUseCase: SearchProductUseCase
) : BaseViewModel() {

    private val _uiGetProductModel = MutableStateFlow(ProductUIModel())
    val uiGetProductModel = _uiGetProductModel.asStateFlow()

    private val _uiSearchProductModel = MutableStateFlow(ProductUIModel())
    val uiSearchProductModel = _uiSearchProductModel.asStateFlow()

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
}