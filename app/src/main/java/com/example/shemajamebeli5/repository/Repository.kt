package com.example.shemajamebeli5.repository

import com.example.shemajamebeli5.RetrofitInstance
import com.example.shemajamebeli5.model.Items
import retrofit2.Response

class Repository {
    suspend fun getPost(): Response<Items>{
        return RetrofitInstance.getData().info()
    }
}