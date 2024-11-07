package com.ql2.myshop.domain.model.product

import com.ql2.myshop.utils.StringExt
import java.io.Serializable


data class ProductModel(
    val productId: Int? = null,
    val cateId: Int? = null,
    val importPrice: Int? = null,
    val salePrice: Int? = null,
    val quantity: Int? = null,
    val description: String? = null,
    val productName: String? = null,
    val productImage: String? = null
): Serializable {
    fun getImages(): List<String> {
        return  StringExt.splitString(productImage = productImage)
    }
}