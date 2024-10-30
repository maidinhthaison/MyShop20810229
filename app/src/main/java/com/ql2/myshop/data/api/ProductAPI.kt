package com.ql2.myshop.data.api

import com.ql2.myshop.data.api.request.UpdateProductRequestDTO
import com.ql2.myshop.data.api.response.ProductResponseDTO
import com.ql2.myshop.data.api.response.UpdateProductByIdResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductAPI {

    @GET("/api/products")
    suspend fun getAllProducts(
    ): Response<List<ProductResponseDTO>>

    @GET("/api/products/cate/{cateId}/pro/{proName}")
    suspend fun searchProducts(
        @Path("cateId") cateId: Int,
        @Path("proName") proName: String
    ): Response<List<ProductResponseDTO>>

    @POST("/api/products/{id}")
    suspend fun updateProductById(
        @Path("id") id: Int,
        @Body request: UpdateProductRequestDTO
    ): Response<UpdateProductByIdResponseDTO>

}