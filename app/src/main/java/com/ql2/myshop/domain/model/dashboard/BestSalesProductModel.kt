package com.ql2.myshop.domain.model.dashboard

import com.ql2.myshop.utils.StringExt
import java.io.Serializable

data class BestSalesProductModel (
    val productId: Int? = null,
    val importPrice: Float? = null,
    val description: String? = null,
    val productName: String? = null,
    val productImage: String? = null,
    val totalSales: String? = null
) : Serializable {
    fun getImages(): List<String> {
        return  StringExt.splitString(productImage = productImage)
    }
}
