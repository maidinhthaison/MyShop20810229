package com.ql2.myshop.domain.model.dashboard

import java.io.Serializable

data class PieChartModel (
    val cateId: Int? = null,
    val cateName: String? = null,
    val numOfProduct: Int? = null
) : Serializable {
    fun toPercent(sumOfProduct : Int): Float {
        return "%.2f".format(numOfProduct?.div(sumOfProduct.toFloat())?.times(100)).toFloat()
    }
}