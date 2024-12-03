package com.ql2.myshop.data.usecase.product

import com.ql2.myshop.data.api.request.GetProductByNameRequestDTO
import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.product.ProductModel
import com.ql2.myshop.domain.repository.product.ProductRepository
import com.ql2.myshop.domain.usecase.product.GetProductByNameUseCase
import kotlinx.coroutines.flow.Flow

class GetProductByNameUseCaseImpl (private val productRepository: ProductRepository) :
    GetProductByNameUseCase {

    override fun invoke(getProductByNameRequestDTO: GetProductByNameRequestDTO): Flow<TaskResult<List<ProductModel>>> {
        return productRepository.getProductsByName(getProductByNameRequestDTO)
    }
}