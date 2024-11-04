package com.ql2.myshop.domain.repository.dashboard

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.dashboard.BestSalesProductModel
import com.ql2.myshop.domain.model.dashboard.OutOfStockProductModel
import com.ql2.myshop.domain.model.dashboard.PieChartModel
import kotlinx.coroutines.flow.Flow

interface DashboardRepository {
    fun groupProductByCate(): Flow<TaskResult<List<PieChartModel>>>
    fun getOutOfStockProduct(limit : Int): Flow<TaskResult<List<OutOfStockProductModel>>>
    fun getBestSaleProduct(limit : Int): Flow<TaskResult<List<BestSalesProductModel>>>
}