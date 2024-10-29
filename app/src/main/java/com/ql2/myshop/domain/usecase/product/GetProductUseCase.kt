package com.ql2.myshop.domain.usecase.product

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.product.ProductModel
import kotlinx.coroutines.flow.Flow

interface GetProductUseCase {
    operator fun invoke(): Flow<TaskResult<List<ProductModel>>>
}