package com.ql2.myshop.domain.usecase.dashboard

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.dashboard.LatestOrderModel
import kotlinx.coroutines.flow.Flow

interface GetLatestOrderUseCase {
    operator fun invoke(): Flow<TaskResult<List<LatestOrderModel>>>
}