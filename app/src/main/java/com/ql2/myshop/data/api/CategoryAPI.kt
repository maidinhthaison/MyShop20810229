package com.ql2.myshop.data.api

import com.ql2.myshop.data.api.request.AddCategoryRequestDTO
import com.ql2.myshop.data.api.request.AddProductRequestDTO
import com.ql2.myshop.data.api.response.AddCategoryResponseDTO
import com.ql2.myshop.data.api.response.CategoryResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CategoryAPI {
    @GET("/api/category")
    suspend fun getAllCategories(
    ): Response<List<CategoryResponseDTO>>

    @POST("/api/category")
    suspend fun insertNewCategory(
        @Body request: AddCategoryRequestDTO
    ): Response<AddCategoryResponseDTO>
}