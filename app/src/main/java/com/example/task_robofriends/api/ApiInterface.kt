package com.example.task_robofriends.api

import com.example.task_robofriends.model.Users
import retrofit2.http.GET

interface ApiInterface {

    @GET("/users")
    suspend fun searchUsers(): MutableList<Users>
}