package com.ql2.myshop.data.api.response

import com.google.gson.annotations.SerializedName
import com.ql2.myshop.domain.model.dashboard.OutOfStockProductModel
import java.io.Serializable

data class OutOfStockProductResponseDTO (
    @SerializedName("product_id") val productId: Int?,
    @SerializedName("cate_id") val cateId: Int?,
    @SerializedName("import_price") val importPrice: Float?,
    @SerializedName("quantity") val quantity: Int?,
    @SerializedName("description") val description: String?,
    @SerializedName("product_name") val productName: String?,
    @SerializedName("product_image") val productImage: String?
): Serializable {
    fun toOutOfStockProductModel(): OutOfStockProductModel {
        return OutOfStockProductModel(
            productId = productId,
            cateId = cateId,
            importPrice = importPrice,
            quantity = quantity,
            description = description,
            productName = productName,
            productImage = productImage
        )
    }
}