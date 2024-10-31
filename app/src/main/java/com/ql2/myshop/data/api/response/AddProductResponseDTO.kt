package com.ql2.myshop.data.api.response

import com.google.gson.annotations.SerializedName
import com.ql2.myshop.domain.model.product.AddProductModel
import com.ql2.myshop.domain.model.product.UpdateProductByIdModel
import java.io.Serializable

data class AddProductResponseDTO (
    @SerializedName("fieldCount") val fieldCount: Int?,
    @SerializedName("affectedRows") val affectedRows: Int?,
    @SerializedName("insertId") val insertId: Int?,
    @SerializedName("info") val info: String?,
    @SerializedName("serverStatus") val serverStatus: Int?,
    @SerializedName("warningStatus") val warningStatus: Int?,
    @SerializedName("changedRows") val changedRows: Int?,
): Serializable {
    fun toAddProductModel(): AddProductModel {
        return AddProductModel(
            fieldCount = fieldCount,
            affectedRows = affectedRows,
            insertId = insertId,
            info = info,
            serverStatus = serverStatus,
            warningStatus = warningStatus,
        )
    }
}