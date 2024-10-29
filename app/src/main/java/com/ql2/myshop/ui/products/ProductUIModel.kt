package com.ql2.myshop.ui.products

import com.ql2.myshop.base.BaseUIModel
import com.ql2.myshop.domain.model.product.ProductModel

data class ProductUIModel (
    override val data: List<ProductModel>?= null,
    override val isLoading: Boolean = false,
    override val messageId: Int? = null
) : BaseUIModel() {
    override fun copyWith(data: Any?, isLoading: Boolean, messageId: Int?): BaseUIModel {
        return this.copy(
            data = (data ?: this.data) as List<ProductModel>?,
            messageId = messageId ?: this.messageId,
            isLoading = isLoading
        )
    }
}