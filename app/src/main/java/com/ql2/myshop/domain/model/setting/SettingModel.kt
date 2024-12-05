package com.ql2.myshop.domain.model.setting

import com.ql2.myshop.utils.SORT

data class SettingModel(val limit: Int? = null, val sort: SORT? = null,
                        val limitDashboard: Int? = null) {
}