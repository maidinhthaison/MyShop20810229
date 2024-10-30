package com.ql2.myshop.data.api

import com.ql2.myshop.data.api.response.CategoryResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface CategoryApi {
    @GET("/api/category")
    suspend fun getAllCategories(
    ): Response<List<CategoryResponseDTO>>
}