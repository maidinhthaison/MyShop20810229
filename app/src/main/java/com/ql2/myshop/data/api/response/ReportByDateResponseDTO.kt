package com.ql2.myshop.data.api.response

import com.google.gson.annotations.SerializedName
import com.ql2.myshop.domain.model.report.ReportByDateModel
import java.io.Serializable

data class ReportByDateResponseDTO (
    @SerializedName("order_id") val orderId: String?,
    @SerializedName("product_id") val productId: Int?,
    @SerializedName("unit_sale_price") val unitSalePrice: Int?,
    @SerializedName("quantity") val quantity: Int?,
    @SerializedName("import_price") val importPrice: Int?,
    @SerializedName("created_time") val createdTime: String?,
    @SerializedName("order_status") val orderStatus: String?,
    @SerializedName("profit") val profit: Int?,
    @SerializedName("revenue") val revenue: Int?,
): Serializable {
    fun toReportByDateModel(): ReportByDateModel {
        return ReportByDateModel(
            orderId = orderId,
            productId = productId,
            unitSalePrice = unitSalePrice,
            quantity = quantity,
            importPrice = importPrice,
            createdTime = createdTime,
            orderStatus = orderStatus,
            profit = profit,
            revenue = revenue
        )
    }
}