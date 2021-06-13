package com.example.get_your_tour_app.repositories.token

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.get_your_tour_app.Constants
import com.example.get_your_tour_app.GetTokenFragment
import com.example.get_your_tour_app.services.Token
import com.example.get_your_tour_app.services.TourService
import com.example.get_your_tour_app.services.dto.TokenDto
import com.example.get_your_tour_app.services.dto.TokenValue
import com.example.get_your_tour_app.services.dto.TourInformationDto
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TokenRepository {

    var token = MutableLiveData<List<TokenValue>>()

    init {
        token.value = mutableListOf()
    }

    private fun getRetrofit(): Token {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(Constants.BaseURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(Token::class.java)
    }

    fun getToken() {
        val token = TokenDto()
        getRetrofit().getToken(token).enqueue(object : Callback<List<TokenValue>> {
            override fun onResponse(call: Call<List<TokenValue>>, response: Response<List<TokenValue>>) {
                Log.d("TAG_", "Entro al callback")
                Log.d("TAG_", response.body().toString())
                response.body()?.get(0)?.let {
                    Constants.Token = "Bearer " + it.token
                    Log.d("TAG_", Constants.Token)
                }
            }

            override fun onFailure(call: Call<List<TokenValue>>, t: Throwable) {
                Log.d("TAG_", "An error in api query in explore")
                Log.d("TAG_", t.message.toString())
            }

        })
    }
}