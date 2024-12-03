package com.ql2.myshop.domain.usecase.product

import com.ql2.myshop.data.api.request.GetProductByCateRequestDTO
import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.product.ProductModel
import kotlinx.coroutines.flow.Flow

interface GetProductByCateUseCase {
    operator fun invoke(getProductByCateRequestDTO: GetProductByCateRequestDTO):
            Flow<TaskResult<List<ProductModel>>>
}