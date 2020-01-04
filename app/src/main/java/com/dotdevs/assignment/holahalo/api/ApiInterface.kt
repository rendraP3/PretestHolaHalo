package com.dotdevs.assignment.holahalo.api

import com.dotdevs.assignment.holahalo.api.model.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("api")
    fun getImage(
        @Query("key") key: String,
        @Query("q") query: String?,
        @Query("category") category: String?,
        @Query("per_page ") page: Int
    ): Call<ApiResponse>

}