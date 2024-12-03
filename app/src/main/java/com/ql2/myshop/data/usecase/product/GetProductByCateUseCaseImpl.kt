package com.ql2.myshop.data.usecase.product

import com.ql2.myshop.data.api.request.GetProductByCateRequestDTO
import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.product.ProductModel
import com.ql2.myshop.domain.repository.product.ProductRepository
import com.ql2.myshop.domain.usecase.product.GetProductByCateUseCase
import kotlinx.coroutines.flow.Flow

class GetProductByCateUseCaseImpl (private val productRepository: ProductRepository) :
    GetProductByCateUseCase {

    override fun invoke(getProductByCateRequestDTO: GetProductByCateRequestDTO): Flow<TaskResult<List<ProductModel>>> {
        return  productRepository.getProductsByCate(getProductByCateRequestDTO)
    }
}