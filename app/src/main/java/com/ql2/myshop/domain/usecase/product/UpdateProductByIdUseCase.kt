package com.ql2.myshop.domain.usecase.product

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.product.ProductModel
import com.ql2.myshop.domain.model.product.UpdateProductByIdModel
import kotlinx.coroutines.flow.Flow

interface UpdateProductByIdUseCase {
    operator fun invoke(productId: Int, salePrice: Int, quantity: Int,
                        description: String, productName: String)
    : Flow<TaskResult<UpdateProductByIdModel>>
}