package com.example.get_your_tour_app.services


import com.example.get_your_tour_app.services.dto.TokenDto
import com.example.get_your_tour_app.services.dto.TokenValue
import retrofit2.Call
import retrofit2.http.*

interface Token {
    @POST("login")
    fun getToken(@Body token: TokenDto): Call<List<TokenValue>>

}