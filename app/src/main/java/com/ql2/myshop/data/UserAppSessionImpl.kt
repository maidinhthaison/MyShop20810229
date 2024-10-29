package com.ql2.myshop.data

import com.ql2.myshop.domain.LocalCache
import com.ql2.myshop.domain.UserAppSession
import com.ql2.myshop.domain.UserAppSession.Companion.KEY_USER
import com.ql2.myshop.domain.model.login.UserModel

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