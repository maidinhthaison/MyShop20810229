package com.ql2.myshop.domain.usecase.report

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.report.ReportByDateModel
import kotlinx.coroutines.flow.Flow

interface GetRevenueProfitUseCase {
    operator fun invoke(from: String, to: String)
            : Flow<TaskResult<List<ReportByDateModel>>>
}