package com.ql2.myshop.ui.products.bottomsheet

import com.ql2.myshop.base.BaseUIModel
import com.ql2.myshop.domain.model.product.AddProductModel
import com.ql2.myshop.domain.model.product.ProductModel

data class AddProductUIModel (
    override val data: AddProductModel?= null,
    override val isLoading: Boolean = false,
    override val messageId: Int? = null
) : BaseUIModel() {
    override fun copyWith(data: Any?, isLoading: Boolean, messageId: Int?): BaseUIModel {
        return this.copy(
            data = (data ?: this.data) as AddProductModel?,
            messageId = messageId ?: this.messageId,
            isLoading = isLoading
        )
    }
}