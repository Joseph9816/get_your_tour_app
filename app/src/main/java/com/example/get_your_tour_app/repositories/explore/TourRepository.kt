package com.example.get_your_tour_app.repositories.explore

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.get_your_tour_app.Constants
import com.example.get_your_tour_app.GetTokenFragment
import com.example.get_your_tour_app.parcelables.SearchParcelable
import com.example.get_your_tour_app.services.dto.SearchDto
import com.example.get_your_tour_app.services.Token
import com.example.get_your_tour_app.services.TourService
import com.example.get_your_tour_app.services.dto.TourDto
import com.example.get_your_tour_app.services.dto.TourInformationDto
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TourRepository {
    var tours = MutableLiveData<List<TourInformationDto>>()

    init {
        tours.value = mutableListOf()
    }

    private fun getRetrofit(): TourService {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(Constants.BaseURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(TourService::class.java)
    }

    fun getTours() {
        Log.d("TAG_", "Entro al metodo getTours ")
        getRetrofit().getTours(Constants.Token, Constants.UserId).enqueue(object : Callback<List<TourInformationDto>> {
            override fun onResponse(call: Call<List<TourInformationDto>>, response: Response<List<TourInformationDto>>) {
                Log.d("TAG_", "Entro al response")
                tours.value = response.body().orEmpty()
                Log.d("TAG_", response.body().toString())
                response.body()?.get(0)?.let {
                    Log.d("TAG_", it.toString())
                }
            }

            override fun onFailure(call: Call<List<TourInformationDto>>, t: Throwable) {
                Log.d("TAG_", "An error getToursInformation")
                Log.d("TAG_", t.message.toString())

            }

        })
    }

    fun getSpecifiedTours(search: SearchParcelable) {
        Log.d("TAG_", "Entro al metodo getTours ")
        val dto = SearchDto(search.start_date, search.end_date, search.place)
        getRetrofit().getSpecifiedTours(Constants.Token, Constants.UserId, dto).enqueue(object : Callback<List<TourInformationDto>> {
            override fun onResponse(call: Call<List<TourInformationDto>>, response: Response<List<TourInformationDto>>) {
                Log.d("TAG_", "Entro al response")
                tours.value = response.body().orEmpty()
                Log.d("TAG_DATA", response.body().toString())
                //if(response.body()?.count() > 0) {
                    /*response.body()?.get(0)?.let {
                        Log.d("TAG_", it.toString())
                    }*/
                //}
            }

            override fun onFailure(call: Call<List<TourInformationDto>>, t: Throwable) {
                Log.d("TAG_", "An error getToursInformation")
                Log.d("TAG_", t.message.toString())

            }

        })
    }
}