package com.ql2.myshop.data.api

import com.ql2.myshop.data.api.response.ProductResponseDTO
import retrofit2.Response
import retrofit2.http.GET
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

}