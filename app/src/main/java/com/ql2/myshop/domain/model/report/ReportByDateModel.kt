package com.ql2.myshop.domain.model.report

import com.ql2.myshop.utils.formatDateTimeServer2BarChart
import java.io.Serializable

data class ReportByDateModel (
    val orderId: String? = null,
    val productId: Int? = null,
    val unitSalePrice: Int? = null,
    val quantity: Int? = null,
    val importPrice: Int? = null,
    val createdTime: String? = null,
    val orderStatus: String? = null,
    val revenue: Int? = null,
    val profit: Int? = null
) : Serializable {
    fun formatDateTime(): String {
        return formatDateTimeServer2BarChart(createdTime ?: "")
    }
}