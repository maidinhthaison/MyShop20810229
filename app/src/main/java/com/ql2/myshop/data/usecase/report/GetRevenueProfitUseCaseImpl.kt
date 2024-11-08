package com.ql2.myshop.data.usecase.report

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.report.ReportByDateModel
import com.ql2.myshop.domain.repository.report.ReportRepository
import com.ql2.myshop.domain.usecase.report.GetRevenueProfitUseCase
import kotlinx.coroutines.flow.Flow

class GetRevenueProfitUseCaseImpl (private val reportRepository: ReportRepository) :
    GetRevenueProfitUseCase {
    override fun invoke(from: String, to: String): Flow<TaskResult<List<ReportByDateModel>>> {
        return reportRepository.getRevenueProfitByDate(from, to)
    }

}