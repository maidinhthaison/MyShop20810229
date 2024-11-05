package com.ql2.myshop.domain.usecase.dashboard

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.dashboard.IncomeInDateModel
import kotlinx.coroutines.flow.Flow

interface GetIncomeInDayUseCase {
    operator fun invoke(): Flow<TaskResult<List<IncomeInDateModel>>>
}