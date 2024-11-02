package com.ql2.myshop.data.repository.orders

import com.ql2.myshop.data.SafeCallAPI
import com.ql2.myshop.data.api.OrdersAPI
import com.ql2.myshop.data.api.request.UpdateOrderByIdRequestDTO
import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.map
import com.ql2.myshop.domain.model.orders.OrderDetailModel
import com.ql2.myshop.domain.model.orders.OrdersModel
import com.ql2.myshop.domain.model.orders.UpdateOrderByIdModel
import com.ql2.myshop.domain.repository.orders.OrdersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class OrdersRepositoryImpl(private val ordersAPI: OrdersAPI,
                           private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO) : OrdersRepository {
    override fun searchOrders(
        status: String,
        dateFrom: String,
        dateTo: String
    ): Flow<TaskResult<List<OrdersModel>>> = flow {
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            ordersAPI.searchOrders(status, dateFrom, dateTo)
        }.map { it.map { it -> it.toOrdersModel() } }
        emit(result)
    }.flowOn(defaultDispatcher)

    override fun getOrderDetail(orderId: String): Flow<TaskResult<List<OrderDetailModel>>> = flow {
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            ordersAPI.getOrderDetail(orderId)
        }.map { it.map { it -> it.toOrderDetailModel() } }
        emit(result)
    }.flowOn(defaultDispatcher)

    override fun updateOrderById(
        status: String,
        orderId: String
    ): Flow<TaskResult<UpdateOrderByIdModel>> = flow<TaskResult<UpdateOrderByIdModel>> {
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            ordersAPI.updateOrderById(orderId, UpdateOrderByIdRequestDTO(status))
        }.map { it.toUpdateOrderByIdModel() }
        emit(result)
    }.flowOn(defaultDispatcher)


}
