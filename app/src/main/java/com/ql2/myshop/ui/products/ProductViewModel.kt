package com.ql2.myshop.ui.products

import androidx.lifecycle.viewModelScope
import com.ql2.myshop.base.BaseViewModel
import com.ql2.myshop.domain.usecase.product.GetProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase
) : BaseViewModel() {

    private val _uiGetProductModel = MutableStateFlow(ProductUIModel())
    val uiGetProductModel = _uiGetProductModel.asStateFlow()

    fun getListProducts() {
        viewModelScope.launch {
            getProductUseCase().collectAsState(_uiGetProductModel)
        }
    }
}