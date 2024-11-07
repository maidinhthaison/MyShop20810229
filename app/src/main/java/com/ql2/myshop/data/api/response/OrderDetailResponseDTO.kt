package com.ql2.myshop.data.api.response

import com.google.gson.annotations.SerializedName
import com.ql2.myshop.domain.model.orders.OrderDetailModel
import java.io.Serializable

data class OrderDetailResponseDTO (
    @SerializedName("order_item_id") val orderItemId: String?,
    @SerializedName("unit_sale_price") val unitSalePrice: Int?,
    @SerializedName("quantity") val quantity: Int?,
    @SerializedName("total_price") val totalPrice: Int?,
    @SerializedName("sale_price") val salePrice: Int?,

    @SerializedName("product_id") val productId: Int?,
    @SerializedName("import_price") val importPrice: Int?,
    @SerializedName("cate_id") val cateId: Int?,
    @SerializedName("product_name") val proName: String?,

    @SerializedName("description") val description: String?,
    @SerializedName("product_image") val productImage: String?
): Serializable {
    fun toOrderDetailModel(): OrderDetailModel {
        return OrderDetailModel(
            orderItemId = orderItemId,
            unitSalePrice = unitSalePrice,
            quantity = quantity,
            totalPrice = totalPrice,
            salePrice = salePrice,
            productId = productId,
            importPrice = importPrice,
            cateId = cateId,
            productName = proName,
            description = description,
            productImage = productImage
        )
    }
}