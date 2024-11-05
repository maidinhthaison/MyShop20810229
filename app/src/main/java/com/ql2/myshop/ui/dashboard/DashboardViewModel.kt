package com.ql2.myshop.ui.dashboard

import androidx.lifecycle.viewModelScope
import com.ql2.myshop.base.BaseViewModel
import com.ql2.myshop.domain.usecase.dashboard.GetBestSalesProductUseCase
import com.ql2.myshop.domain.usecase.dashboard.GetIncomeInDayUseCase
import com.ql2.myshop.domain.usecase.dashboard.GetIncomeInMonthUseCase
import com.ql2.myshop.domain.usecase.dashboard.GetLatestOrderUseCase
import com.ql2.myshop.domain.usecase.dashboard.GetNumberOfProductByCateIdUseCase
import com.ql2.myshop.domain.usecase.dashboard.GetOrdersInDayUseCase
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
    private val getBestSalesProductUseCase: GetBestSalesProductUseCase,
    private val getOrdersInDayUseCase: GetOrdersInDayUseCase,
    private val getIncomeInDayUseCase: GetIncomeInDayUseCase,
    private val getLatestOrderUseCase: GetLatestOrderUseCase,
    private val getIncomeInMonthUseCase: GetIncomeInMonthUseCase
) : BaseViewModel() {
    private val _uiPieChartModel = MutableStateFlow(PieChartUIModel())
    val uiPieChartModel = _uiPieChartModel.asStateFlow()

    private val _uiOutOfStockProductModel = MutableStateFlow(OutOfStockProductUIModel())
    val uiOutOfStockProductModel = _uiOutOfStockProductModel.asStateFlow()

    private val _uiBestSalesProductModel = MutableStateFlow(BestSalesProductUIModel())
    val uiBestSalesProductModel = _uiBestSalesProductModel.asStateFlow()

    private val _uiGetOrderInDayModel = MutableStateFlow(OrderInDayUIModel())
    val uiGetOrderInDayModel = _uiGetOrderInDayModel.asStateFlow()

    private val _uiGetIncomeInDayModel = MutableStateFlow(TotalIncomeByDateUIModel())
    val uiGetIncomeInDayModel = _uiGetIncomeInDayModel.asStateFlow()

    private val _uiGetLatestOrderModel = MutableStateFlow(LatestOrderUIModel())
    val uiGetLatestOrderModel = _uiGetLatestOrderModel.asStateFlow()

    private val _uiGetIncomeInMonthModel = MutableStateFlow(TotalIncomeByDateUIModel())
    val uiGetIncomeInMonthModel = _uiGetIncomeInMonthModel.asStateFlow()

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

    fun getOrderInDay()   {
        viewModelScope.launch {
            getOrdersInDayUseCase().collectAsState(_uiGetOrderInDayModel)
        }
    }

    fun getIncomeInDay()   {
        viewModelScope.launch {
            getIncomeInDayUseCase().collectAsState(_uiGetIncomeInDayModel)
        }
    }

    fun getLatestOrder()   {
        viewModelScope.launch {
            getLatestOrderUseCase().collectAsState(_uiGetLatestOrderModel)
        }
    }

    fun getIncomeInMonth()   {
        viewModelScope.launch {
            getIncomeInMonthUseCase().collectAsState(_uiGetIncomeInMonthModel)
        }
    }
}