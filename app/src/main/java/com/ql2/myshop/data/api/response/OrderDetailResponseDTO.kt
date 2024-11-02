package com.ql2.myshop.data.api.response

import com.google.gson.annotations.SerializedName
import com.ql2.myshop.domain.model.orders.OrderDetailModel
import java.io.Serializable

data class OrderDetailResponseDTO (
    @SerializedName("order_item_id") val orderItemId: String?,
    @SerializedName("unit_sale_price") val unitSalePrice: String?,
    @SerializedName("quantity") val quantity: Float?,
    @SerializedName("total_price") val totalPrice: String?,

    @SerializedName("product_id") val productId: String?,
    @SerializedName("import_price") val importPrice: String?,
    @SerializedName("cate_id") val cateId: Float?,
    @SerializedName("product_name") val proName: String?,

    @SerializedName("description") val description: String?,
    @SerializedName("product_image") val productImage: String?
): Serializable {
    fun toOrderDetailModel(): OrderDetailModel {
        return OrderDetailModel(
            orderItemId = orderItemId,
            unitSalePrice = unitSalePrice?.toFloat(),
            quantity = quantity?.toInt(),
            totalPrice = totalPrice?.toFloat(),

            productId = productId,
            importPrice = importPrice?.toFloat(),
            cateId = cateId?.toInt(),
            productName = proName,
            description = description,
            productImage = productImage
        )
    }
}