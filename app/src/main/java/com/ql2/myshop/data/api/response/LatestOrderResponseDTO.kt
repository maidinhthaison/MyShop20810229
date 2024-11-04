package com.ql2.myshop.data.api.response

import com.google.gson.annotations.SerializedName
import com.ql2.myshop.domain.model.dashboard.LatestOrderModel
import java.io.Serializable

data class LatestOrderResponseDTO (
    @SerializedName("order_item_id") val orderItemId: String?,
    @SerializedName("created_time") val createTime: String?,
    @SerializedName("order_status") val orderStatus: String?,
    @SerializedName("final_price") val finalPrice: Int?,
    @SerializedName("num_of_type_products_in_order") val numOfTypeProductInOrder: String?
): Serializable {
    fun toLatestOrderModel(): LatestOrderModel {
        return LatestOrderModel(
            orderItemId = orderItemId,
            createdTime = createTime,
            orderStatus = orderStatus,
            finalPrice = finalPrice,
            numOfTypeProductInOrder = numOfTypeProductInOrder
        )
    }
}