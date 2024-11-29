package com.ql2.myshop.domain

import com.ql2.myshop.domain.model.config.ConfigModel

interface ConfigServer {
    fun saveConfig(configModel: ConfigModel?)
    fun getConfig(): ConfigModel?
    fun getServer(configModel: ConfigModel?): String = configModel?.server.plus(":${configModel?.port}")
    fun clearConfig()
    companion object {
        const val KEY_CONFIG_SERVER = "key_config_server"
    }
}