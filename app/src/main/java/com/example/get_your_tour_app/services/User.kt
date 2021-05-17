package com.example.get_your_tour_app.services

import com.example.get_your_tour_app.services.dto.UserDto
import com.example.get_your_tour_app.services.dto.UserRegister
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface User {
    @POST("users")
    fun storeUser(@Header("Authorization") authorization: String, @Body user: UserRegister): Call<List<UserDto>>

    @GET("users")
    fun getUsers(@Header("Authorization") authorization: String): Call<List<UserDto>>
}