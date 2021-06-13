package com.example.get_your_tour_app.ui.favorites

import androidx.lifecycle.ViewModel

class FavoritesViewModel : ViewModel() {
    private var mainText: String = ""

    init {
        mainText = "Esta es la sección de los favoritos"
    }

    fun getMainText(): String {
        return mainText
    }

    fun setMainText(value: String) {
        mainText = value
    }
}