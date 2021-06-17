package com.example.get_your_tour_app.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.get_your_tour_app.repositories.profile.ProfileRepository
import com.example.get_your_tour_app.services.dto.UserDto
import com.example.get_your_tour_app.services.dto.UserLog

class ProfileViewModel : ViewModel(){
    private var user = MutableLiveData<List<UserDto>>()
    private var userRepository = ProfileRepository ()
    private var password: String = ""
    private var email: String = ""
    private var name: String = ""

    init {
        password = ""
        email = ""
        user = userRepository.user
    }

    fun login() {
        val log = UserLog(email, password)
        userRepository.login(log)
    }

    fun getUser(): MutableLiveData<List<UserDto>> {
        return user
    }

    fun getPassword(): String {
        return password
    }

    fun getEmail(): String {
        return email
    }

    fun getName(): String {
        return name
    }

    fun setPassword(pass: String) {
        password = pass
    }

    fun setEmail(em: String) {
        email = em
    }

    fun setName(nm: String) {
        name = nm
    }
}