package com.ql2.myshop.ui.dashboard

import com.ql2.myshop.base.BaseUIModel
import com.ql2.myshop.domain.model.dashboard.OutOfStockProductModel

data class OutOfStockProductUIModel (
    override val data: List<OutOfStockProductModel>?= null,
    override val isLoading: Boolean = false,
    override val messageId: Int? = null
) : BaseUIModel() {
    override fun copyWith(data: Any?, isLoading: Boolean, messageId: Int?): BaseUIModel {
        return this.copy(
            data = (data ?: this.data) as List<OutOfStockProductModel>?,
            messageId = messageId ?: this.messageId,
            isLoading = isLoading
        )
    }
}