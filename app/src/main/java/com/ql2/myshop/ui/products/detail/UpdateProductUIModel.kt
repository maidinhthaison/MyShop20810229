package com.ql2.myshop.ui.products.detail

import com.ql2.myshop.base.BaseUIModel
import com.ql2.myshop.domain.model.product.UpdateProductByIdModel

data class UpdateProductUIModel (
    override val data: UpdateProductByIdModel?= null,
    override val isLoading: Boolean = false,
    override val messageId: Int? = null
) : BaseUIModel() {
    override fun copyWith(data: Any?, isLoading: Boolean, messageId: Int?): BaseUIModel {
        return this.copy(
            data = (data ?: this.data) as UpdateProductByIdModel?,
            messageId = messageId ?: this.messageId,
            isLoading = isLoading
        )
    }
}