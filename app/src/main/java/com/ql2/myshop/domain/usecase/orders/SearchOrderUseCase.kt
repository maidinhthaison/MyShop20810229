package com.ql2.myshop.domain.usecase.orders

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.orders.OrdersModel
import kotlinx.coroutines.flow.Flow

interface SearchOrderUseCase {
    operator fun invoke(status : String, dateFrom : String, dateTo : String): Flow<TaskResult<List<OrdersModel>>>
}