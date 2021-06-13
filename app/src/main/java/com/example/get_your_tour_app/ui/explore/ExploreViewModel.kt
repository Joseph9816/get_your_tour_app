package com.example.get_your_tour_app.ui.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.get_your_tour_app.repositories.explore.TourRepository
import com.example.get_your_tour_app.repositories.token.TokenRepository
import com.example.get_your_tour_app.services.dto.TokenValue
import com.example.get_your_tour_app.services.dto.TourDto
import com.example.get_your_tour_app.services.dto.TourInformationDto

class ExploreViewModel : ViewModel(){
    private var tours = MutableLiveData<List<TourInformationDto>>()
    private var tourRepository = TourRepository()
    private var token = MutableLiveData<List<TokenValue>>()
    private var tokenRepository = TokenRepository()

    init {
        tours = tourRepository.tours
        token = tokenRepository.token
    }

    fun getToursList() {
        tourRepository.getTours()
    }

    fun getTokenValue(){
        tokenRepository.getToken()
    }

    fun getTours(): MutableLiveData<List<TourInformationDto>> {
        return tours
    }

    fun getToken(): MutableLiveData<List<TokenValue>> {
        return token
    }
}