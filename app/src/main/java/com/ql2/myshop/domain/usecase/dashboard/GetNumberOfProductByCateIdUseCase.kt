package com.ql2.myshop.domain.usecase.dashboard

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.dashboard.PieChartModel
import kotlinx.coroutines.flow.Flow

interface GetNumberOfProductByCateIdUseCase {
    operator fun invoke(): Flow<TaskResult<List<PieChartModel>>>
}