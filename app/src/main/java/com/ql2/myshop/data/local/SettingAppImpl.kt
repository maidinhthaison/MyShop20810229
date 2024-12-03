package com.ql2.myshop.data.local

import com.ql2.myshop.domain.LocalCache
import com.ql2.myshop.domain.local.SettingApp
import com.ql2.myshop.domain.local.SettingApp.Companion.KEY_SETTING_SCREEN
import com.ql2.myshop.domain.model.setting.SettingModel

class SettingAppImpl (private val cache: LocalCache) : SettingApp {

    private var settingModel: SettingModel? = null

    init {
        settingModel = cache.get(KEY_SETTING_SCREEN, SettingModel::class.java)
    }


    override fun saveSetting(settingModel: SettingModel?) {
        this.settingModel = settingModel
        cache.put(KEY_SETTING_SCREEN, this.settingModel)
    }

    override fun getSetting(): SettingModel? {
        return cache.get(KEY_SETTING_SCREEN, SettingModel::class.java)
    }

    override fun clearSetting() {
        cache.put(KEY_SETTING_SCREEN, null)
        settingModel = null
    }
}