package com.ql2.myshop.data.usecase.dashboard

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.dashboard.OrdersInDayModel
import com.ql2.myshop.domain.repository.dashboard.DashboardRepository
import com.ql2.myshop.domain.usecase.dashboard.GetOrdersInDayUseCase
import kotlinx.coroutines.flow.Flow

class GetOrdersInDayUseCaseImpl (private val dashboardRepository: DashboardRepository) :
    GetOrdersInDayUseCase {
    override fun invoke(): Flow<TaskResult<List<OrdersInDayModel>>> {
        return  dashboardRepository.getOrderInDay()
    }
}