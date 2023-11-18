package com.example.task_robofriends.api

import com.example.task_robofriends.model.Users
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    private val service: ApiInterface

    companion object{
        const val BASE_URL = "https://jsonplaceholder.typicode.com"
    }

    init {

        val retrofit: Retrofit =  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(ApiInterface::class.java)
    }

    suspend fun getDetails(): MutableList<Users> {
        return service.searchUsers()
    }

}