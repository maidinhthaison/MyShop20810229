package com.ql2.myshop.domain.usecase.product

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.product.AddProductModel
import kotlinx.coroutines.flow.Flow

interface AddProductUseCase {
    operator fun invoke(cateId: Int, importPrice: Float, quantity: Int,
                        description: String, productName: String, productImage: String)
            : Flow<TaskResult<AddProductModel>>
}