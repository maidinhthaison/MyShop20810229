package com.ql2.myshop.domain.model.orders

import java.io.Serializable

data class OrderDetailModel (
    val orderItemId: String? = null,
    val unitSalePrice: Int? = null,
    val quantity: Int? = null,
    val totalPrice: Int? = null,
    val productId: Int? = null,
    val importPrice: Int? = null,
    val salePrice: Int? = null,
    val cateId: Int? = null,
    val productName: String? = null,
    val description: String? = null,
    val productImage: String? = null
) : Serializable {

}