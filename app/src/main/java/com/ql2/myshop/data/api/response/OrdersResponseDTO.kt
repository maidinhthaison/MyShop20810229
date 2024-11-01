package com.ql2.myshop.data.api.response

import com.google.gson.annotations.SerializedName
import com.ql2.myshop.domain.model.category.CategoryModel
import com.ql2.myshop.domain.model.orders.OrdersModel
import java.io.Serializable

data class OrdersResponseDTO (
    @SerializedName("order_id") val orderId: String?,
    @SerializedName("created_time") val createdTime: String?,
    @SerializedName("final_price") val finalPrice: Float?,
    @SerializedName("order_status") val orderStatus: String?
): Serializable {
    fun toOrdersModel(): OrdersModel {
        return OrdersModel(
            orderId = orderId,
            createdTime = createdTime,
            finalPrice = finalPrice,
            orderStatus = orderStatus
        )
    }
}