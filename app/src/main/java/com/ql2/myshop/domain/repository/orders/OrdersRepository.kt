package com.ql2.myshop.domain.repository.orders

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.orders.OrderDetailModel
import com.ql2.myshop.domain.model.orders.OrdersModel
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {
    fun searchOrders(status : String, dateFrom : String, dateTo : String): Flow<TaskResult<List<OrdersModel>>>
    fun getOrderDetail(orderId : String): Flow<TaskResult<List<OrderDetailModel>>>
}