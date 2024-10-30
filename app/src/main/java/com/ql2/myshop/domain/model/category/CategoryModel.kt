package com.ql2.myshop.domain.model.category

import java.io.Serializable

data class CategoryModel(
    val cateId: Int? = null,
    val cateName: String? = null,
    val cateDescription: String? = null
) : Serializable {
}