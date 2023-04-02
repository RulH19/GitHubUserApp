package com.example.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.api.ApiConfig
import com.example.githubuser.response.ItemsItem
import com.example.githubuser.response.UsersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<ItemsItem>>()

    init {
        setSearchUsers("Nurul")
    }

    fun setSearchUsers(query: String) {

        val client = ApiConfig.getApiService().getSearchUsers(query)
        client.enqueue(object : Callback<UsersResponse> {
            override fun onResponse(
                call: Call<UsersResponse>,
                response: Response<UsersResponse>,
            ) {

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        listUsers.postValue(response.body()?.items as ArrayList<ItemsItem>)
                    }
                } else {
                    Log.e("Cek salah", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                Log.e("Cek salah", "onFailure: ${t.message}")
            }
        })
    }

    fun getSearchUser(): LiveData<ArrayList<ItemsItem>> {
        return listUsers
    }
}