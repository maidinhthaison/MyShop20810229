package com.ql2.myshop.domain.model.dashboard

import com.ql2.myshop.utils.formatDateTimeServer
import java.io.Serializable

data class LatestOrderModel (
    val orderItemId: String? = null,
    val createdTime: String? = null,
    val orderStatus: String? = null,
    val finalPrice: Int? = null,
    val numOfTypeProductInOrder: String? = null
) : Serializable {
    fun toDateTime(): String? {
        return createdTime?.let { formatDateTimeServer(it) }
    }
}
