package com.example.githubuser.api

import com.example.githubuser.response.UsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import com.example.githubuser.BuildConfig
import com.example.githubuser.response.DetailUserResponse
import com.example.githubuser.response.User
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN,
    ): Call<UsersResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN,
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN,
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String,
        @Header("Authorization")
        authorization: String = BuildConfig.TOKEN,
    ): Call<ArrayList<User>>
}