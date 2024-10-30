package com.ql2.myshop.domain.repository.product

import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.product.ProductModel
import com.ql2.myshop.domain.model.product.UpdateProductByIdModel
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getAllProducts(): Flow<TaskResult<List<ProductModel>>>
    fun searchProducts(cateId: Int, proName: String): Flow<TaskResult<List<ProductModel>>>
    fun updateProductById(productId: Int, importPrice: Float, quantity: Int,
                          description: String, productName: String):
            Flow<TaskResult<UpdateProductByIdModel>>
}