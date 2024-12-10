package com.ql2.myshop.data.api

import com.ql2.myshop.data.api.request.AddProductRequestDTO
import com.ql2.myshop.data.api.request.UpdateProductRequestDTO
import com.ql2.myshop.data.api.response.AddProductResponseDTO
import com.ql2.myshop.data.api.response.ProductResponseDTO
import com.ql2.myshop.data.api.response.UpdateProductByIdResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductAPI {

    @GET("/api/products/{limit}/{offset}")
    suspend fun getAllProducts(
        @Path("limit") limit: Int?,
        @Path("offset") offset: Int?
    ): Response<List<ProductResponseDTO>>

    @GET("/api/products/{cateId}/{limit}/{offset}")
    suspend fun getProductByCate(
        @Path("cateId") cateId: Int?,
        @Path("limit") limit: Int?,
        @Path("offset") offset: Int?
    ): Response<List<ProductResponseDTO>>

    @GET("/api/products/{proName}/{limit}/{offset}")
    suspend fun getProductByName(
        @Path("proName") proName: String?,
        @Path("limit") limit: Int?,
        @Path("offset") offset: Int?
    ): Response<List<ProductResponseDTO>>

    @GET("/api/products/{cateId}/{proName}/{limit}/{offset}")
    suspend fun getProductByCateAndName(
        @Path("cateId") cateId: Int?,
        @Path("proName") proName: String?,
        @Path("limit") limit: Int?,
        @Path("offset") offset: Int?
    ): Response<List<ProductResponseDTO>>

    @POST("/api/products/{id}")
    suspend fun updateProductById(
        @Path("id") id: Int,
        @Body request: UpdateProductRequestDTO
    ): Response<UpdateProductByIdResponseDTO>

    @POST("/api/products")
    suspend fun addNewProduct(
        @Body request: AddProductRequestDTO
    ): Response<AddProductResponseDTO>

}