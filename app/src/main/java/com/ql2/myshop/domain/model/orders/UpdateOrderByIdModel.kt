package com.ql2.myshop.domain.model.orders

import java.io.Serializable

data class UpdateOrderByIdModel (
    val fieldCount: Int? = null,
    val affectedRows: Int?= null,
    val insertId: Int?= null,
    val info: String?= null,
    val serverStatus: Int?= null,
    val warningStatus: Int?= null,
    val changedRows: Int?= null
) : Serializable {
    fun toUpdateOrderByIdModel(): UpdateOrderByIdModel {
        return UpdateOrderByIdModel(
            fieldCount = fieldCount,
            affectedRows = affectedRows,
            insertId = insertId,
            info = info,
            serverStatus = serverStatus,
            warningStatus = warningStatus,
        )
    }
}