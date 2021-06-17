package com.example.get_your_tour_app.services

import com.example.get_your_tour_app.services.dto.UserDto
import com.example.get_your_tour_app.services.dto.UserLog
import com.example.get_your_tour_app.services.dto.UserRegister
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {
    @POST("users")
    fun storeUser(@Header("Authorization") authorization: String, @Body user: UserRegister): Call<List<UserDto>>

    @GET("users")
    fun getUsers(@Header("Authorization") authorization: String): Call<List<UserDto>>

    @POST("users/login")
    fun login(@Header("Authorization") authorization: String, @Body user: UserLog): Call<List<UserDto>>
}