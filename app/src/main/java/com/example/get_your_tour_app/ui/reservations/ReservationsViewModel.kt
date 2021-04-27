package com.example.get_your_tour_app.ui.reservations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReservationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Esta es la sección de reservaciones"
    }
    val text: LiveData<String> = _text
}