package com.example.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.api.ApiConfig
import com.example.githubuser.response.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {
    val user = MutableLiveData<ItemsItem>()

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
}
