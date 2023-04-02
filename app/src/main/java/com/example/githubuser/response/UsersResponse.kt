package com.example.githubuser.response

import com.google.gson.annotations.SerializedName

data class UsersResponse(
    @field:SerializedName("items")
    val items: List<ItemsItem>,
)

data class ItemsItem(

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

