package com.ql2.myshop.data.api

import com.ql2.myshop.data.api.response.OrderDetailResponseDTO
import com.ql2.myshop.data.api.response.OrdersResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface OrdersAPI {
    @GET("/api/orders/status/{status}/from/{dateFrom}/to/{dateTo}")
    suspend fun searchOrders(
        @Path("status") status: String,
        @Path("dateFrom") dateFrom: String,
        @Path("dateTo") dateTo: String
    ): Response<List<OrdersResponseDTO>>

    @GET("/api/orders/detail/{id}")
    suspend fun getOrderDetail(
        @Path("id") id: String
    ): Response<List<OrderDetailResponseDTO>>
}