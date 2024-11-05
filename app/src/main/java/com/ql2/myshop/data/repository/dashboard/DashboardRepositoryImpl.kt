package com.ql2.myshop.data.repository.dashboard

import com.ql2.myshop.data.SafeCallAPI
import com.ql2.myshop.data.api.DashboardAPI
import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.map
import com.ql2.myshop.domain.model.dashboard.BestSalesProductModel
import com.ql2.myshop.domain.model.dashboard.IncomeInDateModel
import com.ql2.myshop.domain.model.dashboard.LatestOrderModel
import com.ql2.myshop.domain.model.dashboard.OrdersInDayModel
import com.ql2.myshop.domain.model.dashboard.OutOfStockProductModel
import com.ql2.myshop.domain.model.dashboard.PieChartModel
import com.ql2.myshop.domain.repository.dashboard.DashboardRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DashboardRepositoryImpl (private val dashboardAPI: DashboardAPI,
                               private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO)
    : DashboardRepository {

    override fun groupProductByCate(): Flow<TaskResult<List<PieChartModel>>> = flow {
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            dashboardAPI.groupProductByCate()
        }.map { it -> it.map { it.toPieChartModel() } }
        emit(result)
    }.flowOn(defaultDispatcher)

    override fun getOutOfStockProduct(limit: Int): Flow<TaskResult<List<OutOfStockProductModel>>> = flow {
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            dashboardAPI.getOutOfStockProduct(limit)
        }.map { it -> it.map { it.toOutOfStockProductModel() } }
        emit(result)
    }.flowOn(defaultDispatcher)

    override fun getBestSaleProduct(limit: Int): Flow<TaskResult<List<BestSalesProductModel>>> = flow<TaskResult<List<BestSalesProductModel>>> {
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            dashboardAPI.getBestSalesProduct(limit)
        }.map { it -> it.map { it.toBestSalesProductModel() } }
        emit(result)
    }.flowOn(defaultDispatcher)

    override fun getOrderInDay(): Flow<TaskResult<List<OrdersInDayModel>>> = flow<TaskResult<List<OrdersInDayModel>>> {
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            dashboardAPI.getAllOrdersInDay()
        }.map { it -> it.map { it.toOrdersInDayModel() } }
        emit(result)
    }.flowOn(defaultDispatcher)

    override fun getIncomeInDay(): Flow<TaskResult<List<IncomeInDateModel>>> = flow<TaskResult<List<IncomeInDateModel>>> {
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            dashboardAPI.getIncomeInDay()
        }.map { it -> it.map { it.toIncomeInDateModel() } }
        emit(result)
    }.flowOn(defaultDispatcher)

    override fun getLatestOrders(): Flow<TaskResult<List<LatestOrderModel>>> = flow<TaskResult<List<LatestOrderModel>>> {
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            dashboardAPI.getLatestOrders()
        }.map { it -> it.map { it.toLatestOrderModel() } }
        emit(result)
    }.flowOn(defaultDispatcher)

    override fun getIncomeInMonth(): Flow<TaskResult<List<IncomeInDateModel>>> = flow<TaskResult<List<IncomeInDateModel>>> {
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            dashboardAPI.getIncomeInMonth()
        }.map { it -> it.map { it.toIncomeInDateModel() } }
        emit(result)
    }.flowOn(defaultDispatcher)


}