package com.ql2.myshop.data.usecase.dashboard

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.dashboard.IncomeInDateModel
import com.ql2.myshop.domain.repository.dashboard.DashboardRepository
import com.ql2.myshop.domain.usecase.dashboard.GetIncomeInDayUseCase
import kotlinx.coroutines.flow.Flow

class GetIncomeInDayUseCaseImpl (private val dashboardRepository: DashboardRepository) :
    GetIncomeInDayUseCase {
    override fun invoke(): Flow<TaskResult<List<IncomeInDateModel>>> {
        return  dashboardRepository.getIncomeInDay()
    }
}