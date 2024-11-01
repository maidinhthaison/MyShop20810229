package com.ql2.myshop.data.usecase.product

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.product.ProductModel
import com.ql2.myshop.domain.repository.product.ProductRepository
import com.ql2.myshop.domain.usecase.product.GetProductUseCase
import com.ql2.myshop.domain.usecase.product.SearchProductUseCase
import kotlinx.coroutines.flow.Flow

class SearchProductUseCaseImpl (private val productRepository: ProductRepository) :
    SearchProductUseCase {

    override fun invoke(cateId: Int, proName: String): Flow<TaskResult<List<ProductModel>>> {
        return productRepository.searchProducts(cateId, proName)
    }

}