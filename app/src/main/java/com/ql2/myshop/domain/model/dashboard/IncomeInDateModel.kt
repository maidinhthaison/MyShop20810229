package com.ql2.myshop.domain.model.dashboard

import com.ql2.myshop.utils.formatDateTimeServer2BarChart
import java.io.Serializable

data class IncomeInDateModel(
     val orderId: String? = null,
     val createdTime: String? = null,
     val orderStatus: String? = null,
     val totalPrice: Int? = null
) : Serializable{
     fun formatDateTime(): String {
          return formatDateTimeServer2BarChart(createdTime ?: "")
     }

}