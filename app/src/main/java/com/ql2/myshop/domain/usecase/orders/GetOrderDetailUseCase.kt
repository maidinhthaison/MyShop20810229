package com.ql2.myshop.domain.usecase.orders

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.orders.OrderDetailModel
import kotlinx.coroutines.flow.Flow

interface GetOrderDetailUseCase {
    operator fun invoke(orderId : String): Flow<TaskResult<List<OrderDetailModel>>>
}