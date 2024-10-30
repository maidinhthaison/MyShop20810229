package com.ql2.myshop.domain.model.product

import java.io.Serializable

data class UpdateProductByIdModel(
    val fieldCount: Int? = null,
    val affectedRows: Int?= null,
    val insertId: Int?= null,
    val info: String?= null,
    val serverStatus: Int?= null,
    val warningStatus: Int?= null,
    val changedRows: Int?= null
) : Serializable