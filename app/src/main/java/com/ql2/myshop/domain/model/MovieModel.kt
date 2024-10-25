package com.ql2.myshop.domain.model

import com.ql2.myshop.data.api.response.MovieItem
import java.io.Serializable

data class MovieModel(

    val page: Int? = null,
    val total_pages: Long? = null,
    val results: List<MovieItem>? = null,
    val total_results: Long? = null

) : Serializable
