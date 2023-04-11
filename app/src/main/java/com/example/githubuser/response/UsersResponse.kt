package com.example.githubuser.response

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @field:SerializedName("items")
    val items: ArrayList<User>,
)



