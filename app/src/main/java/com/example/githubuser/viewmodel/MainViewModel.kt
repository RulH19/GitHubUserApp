package com.example.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.githubuser.SettingPreferences
import com.example.githubuser.api.ApiConfig
import com.example.githubuser.response.ItemsItem
import com.example.githubuser.response.UsersResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences) : ViewModel() {

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

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}