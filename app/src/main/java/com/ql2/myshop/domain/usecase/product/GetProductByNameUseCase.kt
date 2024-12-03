package com.ql2.myshop.domain.usecase.product

import com.ql2.myshop.data.api.request.GetProductByNameRequestDTO
import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.product.ProductModel
import kotlinx.coroutines.flow.Flow

interface GetProductByNameUseCase {
    operator fun invoke(getProductByNameRequestDTO: GetProductByNameRequestDTO):
            Flow<TaskResult<List<ProductModel>>>
}