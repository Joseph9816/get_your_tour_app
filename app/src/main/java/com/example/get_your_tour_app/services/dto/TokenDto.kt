package com.example.get_your_tour_app.services.dto

class TokenDto() {
    private var email = ""
    private var password = ""

    init {
        this.email = "j.jose9816@gmail.com"
        this.password = "admin123"
    }

    fun getEmail(): String {
        return this.email
    }

    fun getPassword(): String {
        return this.password
    }
}