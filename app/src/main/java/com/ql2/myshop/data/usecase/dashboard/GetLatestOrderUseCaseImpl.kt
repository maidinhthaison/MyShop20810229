package com.ql2.myshop.data.usecase.dashboard

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.dashboard.LatestOrderModel
import com.ql2.myshop.domain.repository.dashboard.DashboardRepository
import com.ql2.myshop.domain.usecase.dashboard.GetLatestOrderUseCase
import kotlinx.coroutines.flow.Flow

class GetLatestOrderUseCaseImpl(private val dashboardRepository: DashboardRepository) :
    GetLatestOrderUseCase  {
    override fun invoke(): Flow<TaskResult<List<LatestOrderModel>>> {
        return dashboardRepository.getLatestOrders()
    }
}