package com.ql2.myshop.data.api

import com.ql2.myshop.data.api.response.PieChartResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface DashboardAPI {
    @GET("/api/dashboard/groupProductByCate")
    suspend fun groupProductByCate(
    ): Response<List<PieChartResponseDTO>>
}