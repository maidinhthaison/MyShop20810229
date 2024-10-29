package com.ql2.myshop.domain.model.login

import java.io.Serializable

data class UserModel (

    val username: String? = null,
    val password: String? = null,
    val rememberMe: Boolean? = null

) : Serializable
