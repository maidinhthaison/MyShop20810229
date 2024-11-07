package com.ql2.myshop.data.api.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddProductRequestDTO (
    @SerializedName("cate_id") var cateId: Int? = null,
    @SerializedName("import_price") var importPrice: Int? = null,
    @SerializedName("sale_price") var salePrice: Int? = null,
    @SerializedName("quantity") var quantity: Int? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("product_name") var productName: String? = null,
    @SerializedName("product_image") var productImage: String? = null
) : Serializable