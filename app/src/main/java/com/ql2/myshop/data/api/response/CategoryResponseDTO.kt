package com.ql2.myshop.data.api.response

import com.google.gson.annotations.SerializedName
import com.ql2.myshop.domain.model.category.CategoryModel
import com.ql2.myshop.domain.model.product.ProductModel
import java.io.Serializable

data class CategoryResponseDTO (
    @SerializedName("cate_id") val cateId: Int?,
    @SerializedName("cate_name") val cateName: String?,
    @SerializedName("cate_des") val cateDescription: String?
): Serializable {
    fun toCategoryModel(): CategoryModel {
        return CategoryModel(
            cateId = cateId,
            cateName = cateName,
            cateDescription = cateDescription
        )
    }
}
