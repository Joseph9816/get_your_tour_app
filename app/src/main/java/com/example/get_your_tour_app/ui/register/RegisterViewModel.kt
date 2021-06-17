package com.example.get_your_tour_app.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.get_your_tour_app.repositories.profile.ProfileRepository
import com.example.get_your_tour_app.services.dto.UserDto
import com.example.get_your_tour_app.services.dto.UserLog
import com.example.get_your_tour_app.services.dto.UserRegister

class RegisterViewModel : ViewModel() {
    private var user = MutableLiveData<List<UserDto>>()
    private var userRepository = ProfileRepository ()
    private var password: String = ""
    private var email: String = ""
    private var name: String = ""
    private var lastNames: String = ""
    private var passwordConfirmation: String = ""

    init {
        password = ""
        email = ""
        name = ""
        lastNames = ""
        passwordConfirmation = ""
        user = userRepository.user
    }

    fun register() {
        val re = UserRegister(name, lastNames, email, password, passwordConfirmation)
        userRepository.register(re)
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

    fun getLastNames(): String {
        return lastNames
    }

    fun getPasswordConfirmation(): String {
        return passwordConfirmation
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

    fun setLastNames(nm: String) {
        lastNames = nm
    }

    fun setPasswordConfirmation(nm: String) {
        passwordConfirmation = nm
    }
}