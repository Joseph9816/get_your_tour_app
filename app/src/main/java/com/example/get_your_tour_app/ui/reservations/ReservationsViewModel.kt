package com.example.get_your_tour_app.ui.reservations

import androidx.lifecycle.ViewModel

class ReservationsViewModel: ViewModel() {
    private var mainText: String = ""

    init {
        mainText = "Esta es la sección de reservaciones"
    }

    override fun hashCode(): Int {
        return mainText.hashCode()
    }

    fun getMainText(): String {
        return mainText
    }

    fun setMainText(value: String) {
        mainText = value
    }
}