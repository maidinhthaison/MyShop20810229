package com.ql2.myshop.ui.report

import com.ql2.myshop.base.BaseUIModel
import com.ql2.myshop.domain.model.product.ProductModel
import com.ql2.myshop.domain.model.report.ReportByDateModel

data class ReportByDateUIModel (
    override val data: List<ReportByDateModel>?= null,
    override val isLoading: Boolean = false,
    override val messageId: Int? = null
) : BaseUIModel() {
    override fun copyWith(data: Any?, isLoading: Boolean, messageId: Int?): BaseUIModel {
        return this.copy(
            data = (data ?: this.data) as List<ReportByDateModel>?,
            messageId = messageId ?: this.messageId,
            isLoading = isLoading
        )
    }
}