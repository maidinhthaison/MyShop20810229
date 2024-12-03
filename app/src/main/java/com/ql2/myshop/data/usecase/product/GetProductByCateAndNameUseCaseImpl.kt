package com.ql2.myshop.data.usecase.product

import com.ql2.myshop.data.api.request.GetProductByCateAndNameRequestDTO
import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.product.ProductModel
import com.ql2.myshop.domain.repository.product.ProductRepository
import com.ql2.myshop.domain.usecase.product.GetProductByCateAndNameUseCase
import kotlinx.coroutines.flow.Flow

class GetProductByCateAndNameUseCaseImpl (private val productRepository: ProductRepository) :
    GetProductByCateAndNameUseCase {
    override fun invoke(getProductByCateAndNameRequestDTO: GetProductByCateAndNameRequestDTO):
            Flow<TaskResult<List<ProductModel>>> {
        return productRepository.getProductsByCateAndName(getProductByCateAndNameRequestDTO)
    }
}