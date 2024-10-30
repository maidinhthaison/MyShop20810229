package com.ql2.myshop.ui.category

import com.ql2.myshop.base.BaseUIModel
import com.ql2.myshop.domain.model.category.CategoryModel
import com.ql2.myshop.domain.model.product.ProductModel

data class CategoryUIModel (
    override val data: List<CategoryModel>?= null,
    override val isLoading: Boolean = false,
    override val messageId: Int? = null
) : BaseUIModel() {
    override fun copyWith(data: Any?, isLoading: Boolean, messageId: Int?): BaseUIModel {
        return this.copy(
            data = (data ?: this.data) as List<CategoryModel>?,
            messageId = messageId ?: this.messageId,
            isLoading = isLoading
        )
    }
}