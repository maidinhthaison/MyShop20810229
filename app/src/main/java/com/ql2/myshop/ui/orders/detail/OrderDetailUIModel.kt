package com.ql2.myshop.ui.orders.detail

import com.ql2.myshop.base.BaseUIModel
import com.ql2.myshop.domain.model.orders.OrderDetailModel

data class OrderDetailUIModel (
    override val data: List<OrderDetailModel>?= null,
    override val isLoading: Boolean = false,
    override val messageId: Int? = null
) : BaseUIModel() {
    override fun copyWith(data: Any?, isLoading: Boolean, messageId: Int?): BaseUIModel {
        return this.copy(
            data = (data ?: this.data) as List<OrderDetailModel>?,
            messageId = messageId ?: this.messageId,
            isLoading = isLoading
        )
    }
}