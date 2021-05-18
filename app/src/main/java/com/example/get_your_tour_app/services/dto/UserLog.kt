package com.example.get_your_tour_app.services.dto

class UserLog(email: String, password: String) {
    private var email = ""
    private var password = ""

    init {
        this.email = email
        this.password = password
    }
}