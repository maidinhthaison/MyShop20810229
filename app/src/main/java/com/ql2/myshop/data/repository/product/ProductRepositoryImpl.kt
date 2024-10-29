package com.ql2.myshop.data.repository.product

import com.ql2.myshop.data.SafeCallAPI
import com.ql2.myshop.data.api.ProductAPI
import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.map
import com.ql2.myshop.domain.model.product.ProductModel
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

}