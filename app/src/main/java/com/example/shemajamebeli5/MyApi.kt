package com.example.shemajamebeli5

import com.example.shemajamebeli5.model.Items
import retrofit2.Response
import retrofit2.http.GET

interface MyApi {
    @GET("v3/385c0605-9985-498a-a2d9-9477aba75057")
    suspend fun info(): Response<Items>

}