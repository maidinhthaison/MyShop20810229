package com.ql2.myshop.data.api.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UpdateOrderByIdRequestDTO  (
    @SerializedName("order_status") var orderStatus: String? = null
) : Serializable