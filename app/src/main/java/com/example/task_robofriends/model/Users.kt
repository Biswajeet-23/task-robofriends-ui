package com.example.task_robofriends.model

import com.google.gson.annotations.SerializedName

data class Users (
    @SerializedName("name"  )
    var name   : String,
    @SerializedName("email" )
    var email : String
)