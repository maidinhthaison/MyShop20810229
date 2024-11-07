package com.ql2.myshop.data.api.response

import com.google.gson.annotations.SerializedName
import com.ql2.myshop.domain.model.product.ProductModel
import java.io.Serializable

data class ProductResponseDTO (
    @SerializedName("product_id") val productId: Int?,
    @SerializedName("cate_id") val cateId: Int?,
    @SerializedName("import_price") val importPrice: Int?,
    @SerializedName("sale_price") val salePrice: Int?,
    @SerializedName("quantity") val quantity: Int?,
    @SerializedName("description") val description: String?,
    @SerializedName("product_name") val productName: String?,
    @SerializedName("product_image") val productImage: String?
): Serializable {
    fun toProductModel(): ProductModel {
        return ProductModel(
            productId = productId,
            cateId = cateId,
            importPrice = importPrice,
            salePrice = salePrice,
            quantity = quantity,
            description = description,
            productName = productName,
            productImage = productImage
        )
    }
}
