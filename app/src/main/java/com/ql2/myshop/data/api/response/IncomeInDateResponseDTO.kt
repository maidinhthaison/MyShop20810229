package com.ql2.myshop.data.api.response

import com.google.gson.annotations.SerializedName
import com.ql2.myshop.domain.model.dashboard.IncomeInDateModel
import java.io.Serializable

data class IncomeInDateResponseDTO (
    @SerializedName("order_id") val orderId: String?,
    @SerializedName("created_time") val createTime: String?,
    @SerializedName("order_status") val orderStatus: String?,
    @SerializedName("total_price") val totalPrice: Int?
): Serializable {
    fun toIncomeInDateModel(): IncomeInDateModel {
        return IncomeInDateModel(
            orderId = orderId,
            createdTime = createTime,
            orderStatus = orderStatus,
            totalPrice = totalPrice
        )
    }
}
