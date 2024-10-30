package com.ql2.myshop.ui.category

import androidx.lifecycle.viewModelScope
import com.ql2.myshop.base.BaseViewModel
import com.ql2.myshop.domain.usecase.category.GetAllCategoryUseCase
import com.ql2.myshop.domain.usecase.product.GetProductUseCase
import com.ql2.myshop.ui.products.ProductUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getAllCategoryUseCase: GetAllCategoryUseCase
) : BaseViewModel() {

    private val _uiGetCategoryModel = MutableStateFlow(CategoryUIModel())
    val uiGetCategoryModel = _uiGetCategoryModel.asStateFlow()

    fun getAllCategories() {
        viewModelScope.launch {
            getAllCategoryUseCase().collectAsState(_uiGetCategoryModel)
        }
    }
}