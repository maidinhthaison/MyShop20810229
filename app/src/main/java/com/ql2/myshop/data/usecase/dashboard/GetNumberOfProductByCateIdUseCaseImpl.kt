package com.ql2.myshop.data.usecase.dashboard

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.dashboard.PieChartModel
import com.ql2.myshop.domain.repository.dashboard.DashboardRepository
import com.ql2.myshop.domain.usecase.dashboard.GetNumberOfProductByCateIdUseCase
import kotlinx.coroutines.flow.Flow

class GetNumberOfProductByCateIdUseCaseImpl (private val dashboardRepository: DashboardRepository) :
    GetNumberOfProductByCateIdUseCase {
    override fun invoke(): Flow<TaskResult<List<PieChartModel>>> {
        return dashboardRepository.groupProductByCate()
    }
}