package com.ql2.myshop.config

enum class BackendEnvironment(
    val baseUrl: String
) {

    Dev(
        baseUrl = "http://192.168.1.11:8080"
    ),

    Staging(
        baseUrl = "http://192.168.1.11:8080"
    ),

    Prod(
        baseUrl = "http://192.168.1.11:8080"
    )

}