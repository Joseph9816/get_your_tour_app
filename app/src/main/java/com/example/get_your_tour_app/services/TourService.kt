package com.example.get_your_tour_app.services

import com.example.get_your_tour_app.services.dto.*
import retrofit2.Call
import retrofit2.http.*

interface TourService {
    @GET("tours/information/{user}")
    fun getTours(@Header("Authorization") authorization: String, @Path("user") user: Int): Call<List<TourInformationDto>>

}