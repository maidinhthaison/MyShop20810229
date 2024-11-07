package com.ql2.myshop.data.usecase.product

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.product.UpdateProductByIdModel
import com.ql2.myshop.domain.repository.product.ProductRepository
import com.ql2.myshop.domain.usecase.product.UpdateProductByIdUseCase
import kotlinx.coroutines.flow.Flow

class UpdateProductByIdUseCaseImpl(private val productRepository: ProductRepository)
    : UpdateProductByIdUseCase {

    override fun invoke(
        productId: Int,
        salePrice: Int,
        quantity: Int,
        description: String,
        productName: String
    ): Flow<TaskResult<UpdateProductByIdModel>> {
        return  productRepository.updateProductById(productId, salePrice,
            quantity, description, productName)
    }
}