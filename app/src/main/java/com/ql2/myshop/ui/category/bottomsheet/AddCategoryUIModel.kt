package com.ql2.myshop.ui.category.bottomsheet

import com.ql2.myshop.base.BaseUIModel
import com.ql2.myshop.domain.model.category.AddCategoryModel

data class AddCategoryUIModel (
    override val data: AddCategoryModel?= null,
    override val isLoading: Boolean = false,
    override val messageId: Int? = null
) : BaseUIModel() {
    override fun copyWith(data: Any?, isLoading: Boolean, messageId: Int?): BaseUIModel {
        return this.copy(
            data = (data ?: this.data) as AddCategoryModel?,
            messageId = messageId ?: this.messageId,
            isLoading = isLoading
        )
    }
}