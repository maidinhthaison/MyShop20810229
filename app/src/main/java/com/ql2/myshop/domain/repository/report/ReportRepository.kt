package com.ql2.myshop.domain.repository.report

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.report.ReportByDateModel
import kotlinx.coroutines.flow.Flow

interface ReportRepository {
    fun getRevenueProfitByDate(from: String, to: String):
            Flow<TaskResult<List<ReportByDateModel>>>
}