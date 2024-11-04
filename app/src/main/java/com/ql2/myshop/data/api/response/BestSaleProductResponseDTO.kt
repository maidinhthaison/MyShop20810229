package com.ql2.myshop.data.api.response

import com.google.gson.annotations.SerializedName
import com.ql2.myshop.domain.model.dashboard.BestSalesProductModel
import java.io.Serializable

data class BestSaleProductResponseDTO (
    @SerializedName("product_id") val productId: Int?,
    @SerializedName("product_name") val productName: String?,
    @SerializedName("import_price") val importPrice: Float?,
    @SerializedName("description") val description: String?,
    @SerializedName("product_image") val productImage: String?,
    @SerializedName("total_sales") val totalSales: String?
): Serializable {
    fun toBestSalesProductModel(): BestSalesProductModel {
        return BestSalesProductModel(
            productId = productId,
            productImage = productImage,
            importPrice = importPrice,
            description = description,
            productName = productName,
            totalSales = totalSales
        )
    }
}