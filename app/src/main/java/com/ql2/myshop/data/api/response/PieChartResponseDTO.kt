package com.ql2.myshop.data.api.response

import com.google.gson.annotations.SerializedName
import com.ql2.myshop.domain.model.dashboard.PieChartModel
import com.ql2.myshop.domain.model.orders.OrdersModel
import java.io.Serializable

data class PieChartResponseDTO (
    @SerializedName("cate_id") val cateId: Int?,
    @SerializedName("cate_name") val cateName: String?,
    @SerializedName("NumOfProduct") val numOfProduct: Int?
): Serializable {
    fun toPieChartModel(): PieChartModel {
        return PieChartModel(
            cateId = cateId,
            cateName = cateName,
            numOfProduct = numOfProduct
        )
    }
}