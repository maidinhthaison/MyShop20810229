package com.ql2.myshop.ui.dashboard

import androidx.lifecycle.viewModelScope
import com.ql2.myshop.base.BaseViewModel
import com.ql2.myshop.domain.usecase.dashboard.GetNumberOfProductByCateIdUseCase
import com.ql2.myshop.domain.usecase.orders.GetOrderDetailUseCase
import com.ql2.myshop.domain.usecase.orders.SearchOrderUseCase
import com.ql2.myshop.ui.orders.OrderUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getNumberOfProductByCateIdUseCase: GetNumberOfProductByCateIdUseCase
) : BaseViewModel() {
    private val _uiPieChartModel = MutableStateFlow(PieChartUIModel())
    val uiPieChartModel = _uiPieChartModel.asStateFlow()

    fun generatePieChart()  {
        viewModelScope.launch {
            getNumberOfProductByCateIdUseCase().collectAsState(_uiPieChartModel)
        }
    }
}