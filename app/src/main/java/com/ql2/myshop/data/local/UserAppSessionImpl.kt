package com.ql2.myshop.data.local

import com.ql2.myshop.domain.LocalCache
import com.ql2.myshop.domain.local.UserAppSession
import com.ql2.myshop.domain.model.login.UserModel
import com.ql2.myshop.utils.KEY_USER

class UserAppSessionImpl(private val cache: LocalCache) : UserAppSession {

    private var userModel: UserModel? = null

    init {
        userModel = cache.get(KEY_USER, UserModel::class.java)
    }


    override fun getUser(): UserModel? {
        return cache.get(KEY_USER, UserModel::class.java)
    }

    override fun saveUser(userModel: UserModel?) {
        this.userModel = userModel
        cache.put(KEY_USER, this.userModel)
    }

    override fun clearUser() {
        cache.put(KEY_USER, null)
        userModel = null
    }

}