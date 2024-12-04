package com.ql2.myshop.domain.local

import com.ql2.myshop.domain.model.login.UserModel

interface UserAppSession {

    fun getUser(): UserModel?
    fun saveUser(userModel: UserModel?)
    fun clearUser()

}