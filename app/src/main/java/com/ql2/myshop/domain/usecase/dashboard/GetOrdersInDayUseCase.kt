package com.ql2.myshop.domain.usecase.dashboard

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.dashboard.OrdersInDayModel
import kotlinx.coroutines.flow.Flow

interface GetOrdersInDayUseCase {
    operator fun invoke(): Flow<TaskResult<List<OrdersInDayModel>>>
}