package com.example.shemajamebeli5

import com.example.shemajamebeli5.model.Items
import retrofit2.Response
import retrofit2.http.GET

interface MyApi {
    @GET("v3/c3883846-9559-452c-9bb7-7bf2cdc62f0b")
    suspend fun info(): Response<Items>

}