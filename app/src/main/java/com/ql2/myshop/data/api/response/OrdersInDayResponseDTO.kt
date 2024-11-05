package com.ql2.myshop.data.api.response

import com.google.gson.annotations.SerializedName
import com.ql2.myshop.domain.model.dashboard.OrdersInDayModel
import com.ql2.myshop.domain.model.orders.OrderDetailModel
import java.io.Serializable

data class OrdersInDayResponseDTO (
    @SerializedName("order_item_id") val orderItemId: String?,
    @SerializedName("created_time") val createTime: String?,
    @SerializedName("order_status") val orderStatus: String?,
    @SerializedName("order_id") val orderId: String?,
    @SerializedName("num_of_type_products_in_order") val numOfTypeProductInOrder: String?
): Serializable {
    fun toOrdersInDayModel(): OrdersInDayModel {
        return OrdersInDayModel(
            orderItemId = orderItemId,
            createdTime = createTime,
            orderStatus = orderStatus,
            orderId = orderId,
            numOfTypeProductInOrder = numOfTypeProductInOrder
        )
    }
}