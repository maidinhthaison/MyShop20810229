package com.ql2.myshop.domain.repository.product

import com.ql2.myshop.data.api.request.GetAllProductRequestDTO
import com.ql2.myshop.data.api.request.GetProductByCateAndNameRequestDTO
import com.ql2.myshop.data.api.request.GetProductByCateRequestDTO
import com.ql2.myshop.data.api.request.GetProductByNameRequestDTO
import com.ql2.myshop.domain.TaskResult
import com.ql2.myshop.domain.model.product.AddProductModel
import com.ql2.myshop.domain.model.product.ProductModel
import com.ql2.myshop.domain.model.product.UpdateProductByIdModel
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getAllProducts(getAllProductRequestDTO: GetAllProductRequestDTO): Flow<TaskResult<List<ProductModel>>>

    fun getProductsByCate(getProductByCateRequestDTO: GetProductByCateRequestDTO): Flow<TaskResult<List<ProductModel>>>

    fun getProductsByName(getProductByNameRequestDTO: GetProductByNameRequestDTO): Flow<TaskResult<List<ProductModel>>>

    fun getProductsByCateAndName(getProductByCateAndNameRequestDTO: GetProductByCateAndNameRequestDTO): Flow<TaskResult<List<ProductModel>>>


    fun updateProductById(productId: Int, salePrice: Int, quantity: Int,
                          description: String, productName: String):
            Flow<TaskResult<UpdateProductByIdModel>>

    fun addProduct(cateId: Int, importPrice: Int, quantity: Int,
                   description: String, productName: String, productImage: String, salePrice: Int) :
            Flow<TaskResult<AddProductModel>>
}