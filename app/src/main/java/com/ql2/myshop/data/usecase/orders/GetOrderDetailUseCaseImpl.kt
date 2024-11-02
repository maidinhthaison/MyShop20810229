package com.ql2.myshop.data.usecase.orders

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.orders.OrderDetailModel
import com.ql2.myshop.domain.repository.orders.OrdersRepository
import com.ql2.myshop.domain.usecase.orders.GetOrderDetailUseCase
import kotlinx.coroutines.flow.Flow

class GetOrderDetailUseCaseImpl (private val ordersRepository: OrdersRepository) :
    GetOrderDetailUseCase {

    override fun invoke(orderId: String): Flow<TaskResult<List<OrderDetailModel>>> {
        return ordersRepository.getOrderDetail(orderId)
    }
}