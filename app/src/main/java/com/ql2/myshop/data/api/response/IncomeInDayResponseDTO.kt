package com.ql2.myshop.data.api.response

import com.google.gson.annotations.SerializedName
import com.ql2.myshop.domain.model.dashboard.IncomeInDayModel
import java.io.Serializable

data class IncomeInDayResponseDTO (
    @SerializedName("order_id") val orderId: String?,
    @SerializedName("created_time") val createTime: String?,
    @SerializedName("order_status") val orderStatus: String?,
    @SerializedName("total_price_in_day") val totalPriceInDay: Int?
): Serializable {
    fun toIncomeInDayModel(): IncomeInDayModel {
        return IncomeInDayModel(
            orderId = orderId,
            createdTime = createTime,
            orderStatus = orderStatus,
            totalPriceInDay = totalPriceInDay
        )
    }
}
