package com.ql2.myshop.data.usecase.dashboard

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.dashboard.BestSalesProductModel
import com.ql2.myshop.domain.repository.dashboard.DashboardRepository
import com.ql2.myshop.domain.usecase.dashboard.GetBestSalesProductUseCase
import kotlinx.coroutines.flow.Flow

class GetBestSalesProductUseCaseImpl (private val dashboardRepository: DashboardRepository) :
    GetBestSalesProductUseCase {
    override fun invoke(limit: Int): Flow<TaskResult<List<BestSalesProductModel>>> {
        return dashboardRepository.getBestSaleProduct(limit)
    }
}