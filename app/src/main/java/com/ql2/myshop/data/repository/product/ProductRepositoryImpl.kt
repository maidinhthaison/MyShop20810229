package com.ql2.myshop.data.repository.product

import com.ql2.myshop.data.SafeCallAPI
import com.ql2.myshop.data.api.ProductAPI
import com.ql2.myshop.data.api.request.UpdateProductRequestDTO
import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.map
import com.ql2.myshop.domain.model.product.ProductModel
import com.ql2.myshop.domain.model.product.UpdateProductByIdModel
import com.ql2.myshop.domain.repository.product.ProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductRepositoryImpl (private val productAPI: ProductAPI,
                             private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO)
    : ProductRepository {

    override fun getAllProducts(): Flow<TaskResult<List<ProductModel>>> = flow {
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            productAPI.getAllProducts()
        }.map { it -> it.map { it.toProductModel() } }
        emit(result)
    }.flowOn(defaultDispatcher)

    override fun searchProducts(cateId: Int, proName: String): Flow<TaskResult<List<ProductModel>>> = flow<TaskResult<List<ProductModel>>> {
        emit(TaskResult.Loading)
        val result = SafeCallAPI.callApi {
            productAPI.searchProducts(cateId = cateId, proName = proName)
        }.map { it -> it.map { it.toProductModel() } }
        emit(result)
    }.flowOn(defaultDispatcher)

    override fun updateProductById(
        productId: Int,
        importPrice: Float,
        quantity: Int,
        description: String,
        productName: String
    ): Flow<TaskResult<UpdateProductByIdModel>> = flow {
        emit(TaskResult.Loading)
        val updateProductRequestDTO = UpdateProductRequestDTO(importPrice = importPrice,
            quantity = quantity, description = description, productName = productName)
        val result = SafeCallAPI.callApi {
            productAPI.updateProductById(id = productId, request = updateProductRequestDTO)
        }.map { it.toUpdateProductByIdModel()}
        emit(result)
    }.flowOn(defaultDispatcher)

}