package com.ql2.myshop.data.usecase.dashboard

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.dashboard.OutOfStockProductModel
import com.ql2.myshop.domain.repository.dashboard.DashboardRepository
import com.ql2.myshop.domain.usecase.dashboard.GetOutOfStockProductUseCase
import kotlinx.coroutines.flow.Flow

class GetOutOfStockProductUseCaseImpl (private val dashboardRepository: DashboardRepository) :
    GetOutOfStockProductUseCase {
    override fun invoke(limit: Int): Flow<TaskResult<List<OutOfStockProductModel>>> {
        return dashboardRepository.getOutOfStockProduct(limit)
    }
}