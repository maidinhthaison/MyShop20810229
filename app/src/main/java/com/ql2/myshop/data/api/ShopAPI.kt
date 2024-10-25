package com.ql2.myshop.data.api

import com.ql2.myshop.config.API_VERSION
import com.ql2.myshop.data.api.response.MovieResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShopAPI {

    @GET("/{version}/discover/movie")
    suspend fun getMovies(
        @Path("version") version: String = API_VERSION,
        @Query("include_adult") include_adult: Boolean?,
        @Query("include_video") include_video: Boolean?,
        @Query("language") language: String?,
        @Query("page") page: Int?,
        @Query("sort_by") sort_by: String?
    ): Response<MovieResponseDto>

}