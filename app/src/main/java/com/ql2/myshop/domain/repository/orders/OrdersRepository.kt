package com.ql2.myshop.domain.repository.orders

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.orders.OrderDetailModel
import com.ql2.myshop.domain.model.orders.OrdersModel
import com.ql2.myshop.domain.model.orders.UpdateOrderByIdModel
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {
    fun searchOrders(status : String, dateFrom : String, dateTo : String): Flow<TaskResult<List<OrdersModel>>>
    fun getOrderDetail(orderId : String): Flow<TaskResult<List<OrderDetailModel>>>
    fun updateOrderById(status : String, orderId : String): Flow<TaskResult<UpdateOrderByIdModel>>
}