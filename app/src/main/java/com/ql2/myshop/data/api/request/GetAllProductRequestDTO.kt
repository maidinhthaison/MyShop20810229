package com.ql2.myshop.data.api.request

data class GetAllProductRequestDTO(
    val limit: Int? = null,
    val offset: Int? = null ,
)
data class GetProductByCateRequestDTO(
    val limit: Int? = null,
    val offset: Int? = null ,
    val cateId: Int? = null,
)

data class GetProductByNameRequestDTO(
    val limit: Int? = null,
    val offset: Int? = null ,
    val proName: String? = null
)

data class GetProductByCateAndNameRequestDTO(
    val limit: Int? = null,
    val offset: Int? = null ,
    val cateId: Int? = null,
    val proName: String? = null
)

enum class SortBy{
    PRICE, CREATE_DATE
}
enum class SortDirection{
    ASC, DESC
}
const val LIMIT_DEFAULT : Int = 10
const val TOP_LIMIT_DEFAULT : Int = 5