package com.example.get_your_tour_app.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.get_your_tour_app.repositories.explore.TourRepository
import com.example.get_your_tour_app.repositories.token.TokenRepository
import com.example.get_your_tour_app.services.dto.TokenValue
import com.example.get_your_tour_app.services.dto.TourInformationDto
import com.example.get_your_tour_app.parcelables.SearchParcelable

class SearchViewModel : ViewModel(){
    private var tours = MutableLiveData<List<TourInformationDto>>()
    private var tourRepository = TourRepository()

    init {
        tours = tourRepository.tours
    }

    fun getSpecifiedTours(search: SearchParcelable) {
        tourRepository.getSpecifiedTours(search)
    }

    fun getTours(): MutableLiveData<List<TourInformationDto>> {
        return tours
    }
}