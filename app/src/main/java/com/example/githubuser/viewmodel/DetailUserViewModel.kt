package com.example.githubuser.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuser.api.ApiConfig
import com.example.githubuser.local.FavoriteUser
import com.example.githubuser.local.FavoriteUserDao
import com.example.githubuser.local.UserDatabase
import com.example.githubuser.response.ItemsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<ItemsItem>()
    private var userDao: FavoriteUserDao?
    private var userDb: UserDatabase?

    init {
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun setUserDetail(username: String) {

        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<ItemsItem> {
            override fun onResponse(
                call: Call<ItemsItem>,
                response: Response<ItemsItem>,
            ) {

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        user.postValue(response.body())
                    }
                } else {
                    Log.e("Cek salah", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ItemsItem>, t: Throwable) {
                Log.e("Cek salah", "onFailure: ${t.message}")
            }
        })
    }

    fun getUserDetail(): LiveData<ItemsItem> {
        return user
    }

    fun addToFavorite(username: String, id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(
                username,
                id
            )
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFavorite(id)
        }
    }
}
