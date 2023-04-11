package com.example.githubuser.response

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(
    val id: Int,
    @field:SerializedName("login")
    val login: String,
    @field:SerializedName("avatar_url")
    val avatarUrl: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("following")
    val following: Int,
    @field:SerializedName("followers")
    val followers: Int,
)
