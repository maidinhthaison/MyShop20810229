package com.ql2.myshop.ui.dashboard

import androidx.lifecycle.viewModelScope
import com.ql2.myshop.base.BaseViewModel
import com.ql2.myshop.domain.usecase.dashboard.GetBestSalesProductUseCase
import com.ql2.myshop.domain.usecase.dashboard.GetNumberOfProductByCateIdUseCase
import com.ql2.myshop.domain.usecase.dashboard.GetOutOfStockProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getNumberOfProductByCateIdUseCase: GetNumberOfProductByCateIdUseCase,
    private val getOutOfStockProductUseCase: GetOutOfStockProductUseCase,
    private val getBestSalesProductUseCase: GetBestSalesProductUseCase
) : BaseViewModel() {
    private val _uiPieChartModel = MutableStateFlow(PieChartUIModel())
    val uiPieChartModel = _uiPieChartModel.asStateFlow()

    private val _uiOutOfStockProductModel = MutableStateFlow(OutOfStockProductUIModel())
    val uiOutOfStockProductModel = _uiOutOfStockProductModel.asStateFlow()

    private val _uiBestSalesProductModel = MutableStateFlow(BestSalesProductUIModel())
    val uiBestSalesProductModel = _uiBestSalesProductModel.asStateFlow()

    fun generatePieChart()  {
        viewModelScope.launch {
            getNumberOfProductByCateIdUseCase().collectAsState(_uiPieChartModel)
        }
    }

    fun getOutOfStockProducts(limit: Int)  {
        viewModelScope.launch {
            getOutOfStockProductUseCase(limit).collectAsState(_uiOutOfStockProductModel)
        }
    }

    fun getBestSalesProducts(limit: Int)  {
        viewModelScope.launch {
            getBestSalesProductUseCase(limit).collectAsState(_uiBestSalesProductModel)
        }
    }
}