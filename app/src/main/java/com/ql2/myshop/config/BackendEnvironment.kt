package com.ql2.myshop.config

import com.ql2.myshop.BuildConfig


enum class BackendEnvironment(
    val baseUrl: String
) {

    Dev(
        baseUrl = BuildConfig.BASE_API_URL
    ),

    Stag(
        baseUrl = BuildConfig.BASE_API_URL
    ),

    Prod(
        baseUrl = BuildConfig.BASE_API_URL
    )
}
