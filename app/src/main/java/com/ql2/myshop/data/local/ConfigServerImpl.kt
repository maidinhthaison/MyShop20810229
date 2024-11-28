package com.ql2.myshop.data.local

import com.ql2.myshop.domain.ConfigServer
import com.ql2.myshop.domain.ConfigServer.Companion.KEY_CONFIG_SERVER
import com.ql2.myshop.domain.LocalCache
import com.ql2.myshop.domain.UserAppSession
import com.ql2.myshop.domain.UserAppSession.Companion.KEY_USER
import com.ql2.myshop.domain.model.config.ConfigModel
import com.ql2.myshop.domain.model.login.UserModel

class ConfigServerImpl (private val cache: LocalCache) : ConfigServer {

    private var configModel: ConfigModel? = null

    init {
        configModel = cache.get(KEY_CONFIG_SERVER, ConfigModel::class.java)
    }

    override fun saveConfig(configModel: ConfigModel?) {
        this.configModel = configModel
        cache.put(KEY_CONFIG_SERVER, this.configModel)
    }

    override fun getConfig(): ConfigModel? {
        return cache.get(KEY_CONFIG_SERVER, ConfigModel::class.java)
    }

    override fun clearConfig() {
        cache.put(KEY_USER, null)
        configModel = null
    }
}