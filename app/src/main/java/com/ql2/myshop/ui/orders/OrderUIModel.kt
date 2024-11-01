package com.ql2.myshop.ui.orders

import com.ql2.myshop.base.BaseUIModel
import com.ql2.myshop.domain.model.orders.OrdersModel

data class OrderUIModel  (
    override val data: List<OrdersModel>?= null,
    override val isLoading: Boolean = false,
    override val messageId: Int? = null
) : BaseUIModel() {
    override fun copyWith(data: Any?, isLoading: Boolean, messageId: Int?): BaseUIModel {
        return this.copy(
            data = (data ?: this.data) as List<OrdersModel>?,
            messageId = messageId ?: this.messageId,
            isLoading = isLoading
        )
    }
}