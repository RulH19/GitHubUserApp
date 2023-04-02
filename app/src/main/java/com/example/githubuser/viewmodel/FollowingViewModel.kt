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

class FollowingViewModel : ViewModel() {

    val listFollowing = MutableLiveData<ArrayList<ItemsItem>>()

    fun setListFollowing(username: String) {
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<ArrayList<ItemsItem>> {
            override fun onResponse(
                call: Call<ArrayList<ItemsItem>>,
                response: Response<ArrayList<ItemsItem>>,
            ) {

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        listFollowing.postValue(response.body())
                    }
                } else {
                    Log.e("Cek salah", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<ItemsItem>>, t: Throwable) {
                Log.e("Cek salah", "onFailure: ${t.message}")
            }
        })
    }

    fun getListFollowing(): LiveData<ArrayList<ItemsItem>> {
        return listFollowing
    }
}
