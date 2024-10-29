package com.ql2.myshop.data.usecase

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.product.ProductModel
import com.ql2.myshop.domain.repository.product.ProductRepository
import com.ql2.myshop.domain.usecase.product.GetProductUseCase
import kotlinx.coroutines.flow.Flow

class GetProductUseCaseImpl(private val productRepository: ProductRepository) : GetProductUseCase {
    override fun invoke(): Flow<TaskResult<List<ProductModel>>> {
        return productRepository.getAllProducts()
    }
}