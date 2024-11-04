package com.ql2.myshop.domain.usecase.dashboard

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.dashboard.OutOfStockProductModel
import kotlinx.coroutines.flow.Flow

interface GetOutOfStockProductUseCase {
    operator fun invoke(limit: Int): Flow<TaskResult<List<OutOfStockProductModel>>>
}