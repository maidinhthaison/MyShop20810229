package com.ql2.myshop.domain.usecase.orders

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.orders.UpdateOrderByIdModel
import kotlinx.coroutines.flow.Flow

interface UpdateOrderByIdUseCase {
    operator fun invoke(status : String, orderId : String): Flow<TaskResult<UpdateOrderByIdModel>>
}