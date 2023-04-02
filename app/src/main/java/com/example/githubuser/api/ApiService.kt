package com.example.githubuser.api

import com.example.githubuser.response.ItemsItem
import com.example.githubuser.response.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String,
    ): Call<UsersResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String,
    ): Call<ItemsItem>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String,
    ): Call<ArrayList<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String,
    ): Call<ArrayList<ItemsItem>>
}