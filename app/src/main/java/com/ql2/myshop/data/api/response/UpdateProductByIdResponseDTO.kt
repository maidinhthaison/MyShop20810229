package com.ql2.myshop.data.api.response

import com.google.gson.annotations.SerializedName
import com.ql2.myshop.domain.model.category.CategoryModel
import com.ql2.myshop.domain.model.product.UpdateProductByIdModel
import java.io.Serializable

data class UpdateProductByIdResponseDTO (
    @SerializedName("fieldCount") val fieldCount: Int?,
    @SerializedName("affectedRows") val affectedRows: Int?,
    @SerializedName("insertId") val insertId: Int?,
    @SerializedName("info") val info: String?,
    @SerializedName("serverStatus") val serverStatus: Int?,
    @SerializedName("warningStatus") val warningStatus: Int?,
    @SerializedName("changedRows") val changedRows: Int?,
): Serializable {
    fun toUpdateProductByIdModel(): UpdateProductByIdModel {
        return UpdateProductByIdModel(
            fieldCount = fieldCount,
            affectedRows = affectedRows,
            insertId = insertId,
            info = info,
            serverStatus = serverStatus,
            warningStatus = warningStatus,
        )
    }
}
