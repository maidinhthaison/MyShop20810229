package com.ql2.myshop.domain.local

import com.ql2.myshop.domain.model.setting.SettingModel

interface SettingApp {
    fun saveSetting(settingModel: SettingModel?)
    fun getSetting(): SettingModel?
    fun clearSetting()
    companion object {
        const val KEY_SETTING_SCREEN = "key_setting_screen"
    }
}