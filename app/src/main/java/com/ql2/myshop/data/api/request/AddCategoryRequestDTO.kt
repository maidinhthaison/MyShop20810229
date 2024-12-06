package com.ql2.myshop.data.api.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AddCategoryRequestDTO(
    @SerializedName("cate_name") var cateName: String?,
    @SerializedName("cate_des") var cateDescription: String?) : Serializable