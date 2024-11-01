package com.ql2.myshop.data.usecase.orders

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.orders.OrdersModel
import com.ql2.myshop.domain.repository.orders.OrdersRepository
import com.ql2.myshop.domain.usecase.orders.SearchOrderUseCase
import kotlinx.coroutines.flow.Flow

class SearchOrderUseCaseImpl (private val ordersRepository: OrdersRepository) :
    SearchOrderUseCase {

    override fun invoke(
        status: String,
        dateFrom: String,
        dateTo: String
    ): Flow<TaskResult<List<OrdersModel>>> {
        return ordersRepository.searchOrders(status, dateFrom, dateTo)
    }
}