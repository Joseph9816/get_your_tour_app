package com.example.get_your_tour_app.repositories.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.get_your_tour_app.Constants
import com.example.get_your_tour_app.services.TourService
import com.example.get_your_tour_app.services.UserService
import com.example.get_your_tour_app.services.dto.UserDto
import com.example.get_your_tour_app.services.dto.UserLog
import com.example.get_your_tour_app.services.dto.UserRegister
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileRepository {
    var user = MutableLiveData<List<UserDto>>()

    init {
        user.value = mutableListOf()
    }

    private fun getRetrofit(): UserService {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(Constants.BaseURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(UserService::class.java)
    }

    fun login(userData: UserLog) {
        getRetrofit().login(Constants.Token, userData).enqueue(object : Callback<List<UserDto>> {
            override fun onResponse(call: Call<List<UserDto>>, response: Response<List<UserDto>>) {
                Log.d("TAG_", "Entro al response")
                user.value = response.body().orEmpty()
            }

            override fun onFailure(call: Call<List<UserDto>>, t: Throwable) {
                Log.d("TAG_", "An error user login")
                Log.d("TAG_", t.message.toString())
            }

        })
    }

    fun register(userData: UserRegister) {
        getRetrofit().storeUser(Constants.Token, userData).enqueue(object : Callback<List<UserDto>> {
            override fun onResponse(call: Call<List<UserDto>>, response: Response<List<UserDto>>) {
                Log.d("TAG_", "Entro al response")
                user.value = response.body().orEmpty()
            }

            override fun onFailure(call: Call<List<UserDto>>, t: Throwable) {
                Log.d("TAG_", "An error user register")
                Log.d("TAG_", t.message.toString())
            }

        })
    }
}