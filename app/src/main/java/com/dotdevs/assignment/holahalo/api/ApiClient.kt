package com.dotdevs.assignment.holahalo.api

import com.dotdevs.assignment.holahalo.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val service: ApiInterface = retrofit.create(ApiInterface::class.java)

}