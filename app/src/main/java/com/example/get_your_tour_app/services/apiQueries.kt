package com.example.get_your_tour_app.services


import com.example.get_your_tour_app.services.dto.UserDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface apiQueries {
    @POST("/users")
    fun storeUser(/*@Body */): Call<List<UserDto>>
}