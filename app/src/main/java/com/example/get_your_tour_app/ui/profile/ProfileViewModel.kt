package com.example.get_your_tour_app.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Esta es la sección del perfil de usuario"
    }
    val text: LiveData<String> = _text
}