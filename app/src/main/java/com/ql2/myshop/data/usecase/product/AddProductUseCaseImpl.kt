package com.ql2.myshop.data.usecase.product

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.product.AddProductModel
import com.ql2.myshop.domain.repository.product.ProductRepository
import com.ql2.myshop.domain.usecase.product.AddProductUseCase
import kotlinx.coroutines.flow.Flow

class AddProductUseCaseImpl (private val productRepository: ProductRepository)
    : AddProductUseCase {

    override fun invoke(
        cateId: Int,
        importPrice: Float,
        quantity: Int,
        description: String,
        productName: String,
        productImage: String
    ): Flow<TaskResult<AddProductModel>> {
        return productRepository.addProduct(cateId, importPrice, quantity, description, productName, productImage)
    }
}