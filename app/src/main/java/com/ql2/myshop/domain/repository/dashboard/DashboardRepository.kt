package com.ql2.myshop.domain.repository.dashboard

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.dashboard.PieChartModel
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    fun groupProductByCate(): Flow<TaskResult<List<PieChartModel>>>
}