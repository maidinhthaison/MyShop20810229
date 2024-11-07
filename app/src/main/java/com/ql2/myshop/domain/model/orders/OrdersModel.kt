package com.ql2.myshop.domain.model.orders

import java.io.Serializable

data class OrdersModel (
    val orderId: String? = null,
    val createdTime: String? = null,
    val finalPrice: Int? = null,
    val orderStatus: String? = null
) : Serializable