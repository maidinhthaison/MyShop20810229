package com.ql2.myshop.data.api

import com.ql2.myshop.data.api.response.BestSaleProductResponseDTO
import com.ql2.myshop.data.api.response.OutOfStockProductResponseDTO
import com.ql2.myshop.data.api.response.PieChartResponseDTO
import com.ql2.myshop.domain.model.dashboard.OutOfStockProductModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DashboardAPI {
    @GET("/api/dashboard/groupProductByCate")
    suspend fun groupProductByCate(
    ): Response<List<PieChartResponseDTO>>

    @GET("/api/products/outofstock/{limit}")
    suspend fun getOutOfStockProduct(
        @Path("limit") limit: Int,
    ): Response<List<OutOfStockProductResponseDTO>>

    @GET("/api/products/bestsale/{limit}")
    suspend fun getBestSalesProduct(
        @Path("limit") limit: Int,
    ): Response<List<BestSaleProductResponseDTO>>

}