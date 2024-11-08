package com.ql2.myshop.data.repository.report

import com.ql2.myshop.data.SafeCallAPI
import com.ql2.myshop.data.api.ReportAPI
import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.map
import com.ql2.myshop.domain.model.report.ReportByDateModel
import com.ql2.myshop.domain.repository.report.ReportRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ReportRepositoryImpl (private val reportAPI: ReportAPI,
                            private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO)
    : ReportRepository {

    override fun getRevenueProfitByDate(
        from: String,
        to: String
    ): Flow<TaskResult<List<ReportByDateModel>>> = flow<TaskResult<List<ReportByDateModel>>> {
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            reportAPI.getRevenueProfit(dateFrom = from, dateTo = to)
        }.map { it -> it.map { it.toReportByDateModel() } }
        emit(result)
    }.flowOn(defaultDispatcher)


}