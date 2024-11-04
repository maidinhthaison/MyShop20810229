package com.ql2.myshop.domain.model.dashboard

import java.io.Serializable

data class IncomeInDayModel(
     val orderId: String? = null,
     val createdTime: String? = null,
     val orderStatus: String? = null,
     val totalPriceInDay: Int? = null
) : Serializable{
}