package com.ql2.myshop.ui.orders

import androidx.lifecycle.viewModelScope
import com.ql2.myshop.base.BaseViewModel
import com.ql2.myshop.domain.usecase.orders.GetOrderDetailUseCase
import com.ql2.myshop.domain.usecase.orders.SearchOrderUseCase
import com.ql2.myshop.ui.orders.detail.OrderDetailUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val searchOrderUseCase: SearchOrderUseCase,
    private val getOrderDetailUseCase: GetOrderDetailUseCase
) : BaseViewModel() {

    private val _uiSearchOrderModel = MutableStateFlow(OrderUIModel())
    val uiSearchOrderModel = _uiSearchOrderModel.asStateFlow()

    private val _uiGetOrderDetailModel = MutableStateFlow(OrderDetailUIModel())
    val uiGetOrderDetailModel = _uiGetOrderDetailModel.asStateFlow()

    fun searchOrders(status: String, dateFrom: String, dateTo: String) {
        viewModelScope.launch {
            searchOrderUseCase(status, dateFrom, dateTo).collectAsState(_uiSearchOrderModel)
        }
    }

    fun getOrderDetail(orderId: String)  {
        viewModelScope.launch {
            getOrderDetailUseCase(orderId).collectAsState(_uiGetOrderDetailModel)
        }
    }
}