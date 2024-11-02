package com.ql2.myshop.data.usecase.orders

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.orders.UpdateOrderByIdModel
import com.ql2.myshop.domain.repository.orders.OrdersRepository
import com.ql2.myshop.domain.usecase.orders.UpdateOrderByIdUseCase
import kotlinx.coroutines.flow.Flow

class UpdateOrderByIdUseCaseImpl(private val ordersRepository: OrdersRepository) : UpdateOrderByIdUseCase {
    override fun invoke(status: String, orderId: String): Flow<TaskResult<UpdateOrderByIdModel>> {
        return ordersRepository.updateOrderById(status, orderId)
    }
}