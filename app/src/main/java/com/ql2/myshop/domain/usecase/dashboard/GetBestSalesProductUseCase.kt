package com.ql2.myshop.domain.usecase.dashboard

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.dashboard.BestSalesProductModel
import kotlinx.coroutines.flow.Flow

interface GetBestSalesProductUseCase {
    operator fun invoke(limit: Int): Flow<TaskResult<List<BestSalesProductModel>>>
}