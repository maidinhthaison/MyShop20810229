package com.ql2.myshop.domain

import com.ql2.myshop.domain.model.login.UserModel

interface UserAppSession {

    fun getUser(): UserModel?
    fun saveUser(userModel: UserModel?)

    fun clearUser()

    companion object{
        const val MOCK_USERNAME = "admin"
        const val MOCK_PASSWORD = "123456"

        const val KEY_USER = "user"
    }
}