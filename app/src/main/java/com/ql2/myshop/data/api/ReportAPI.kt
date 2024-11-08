package com.ql2.myshop.data.api

import com.ql2.myshop.data.api.response.ReportByDateResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ReportAPI {
    @GET("/api/report/{from}/{to}")
    suspend fun getRevenueProfit(
        @Path("from") dateFrom: String,
        @Path("to") dateTo: String
    ): Response<List<ReportByDateResponseDTO>>
}