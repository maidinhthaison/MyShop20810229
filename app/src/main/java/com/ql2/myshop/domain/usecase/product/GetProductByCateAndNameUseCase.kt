package com.ql2.myshop.domain.usecase.product

import com.ql2.myshop.data.api.request.GetProductByCateAndNameRequestDTO
import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.product.ProductModel
import kotlinx.coroutines.flow.Flow

interface GetProductByCateAndNameUseCase {
    operator fun invoke(getProductByCateAndNameRequestDTO: GetProductByCateAndNameRequestDTO):
            Flow<TaskResult<List<ProductModel>>>
}