package com.ql2.myshop.data.api

import com.ql2.myshop.data.api.response.ProductResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface ProductAPI {

    @GET("/api/products")
    suspend fun getAllProducts(
    ): Response<List<ProductResponseDTO>>

    @GET("/api/products/:id")
    suspend fun searchProducts(
    ): Response<List<ProductResponseDTO>>

}