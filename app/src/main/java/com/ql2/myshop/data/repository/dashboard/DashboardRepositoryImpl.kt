package com.ql2.myshop.data.repository.dashboard

import com.ql2.myshop.data.SafeCallAPI
import com.ql2.myshop.data.api.DashboardAPI
import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.map
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


}