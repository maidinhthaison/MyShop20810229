package com.ql2.myshop.domain.model.orders

import java.io.Serializable

data class OrderDetailModel (
    val orderItemId: String? = null,
    val unitSalePrice: Float? = null,
    val quantity: Int? = null,
    val totalPrice: Float? = null,
    val productId: String? = null,
    val importPrice: Float? = null,
    val cateId: Int? = null,
    val productName: String? = null,
    val description: String? = null,
    val productImage: String? = null
) : Serializable {

}