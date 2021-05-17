package com.example.get_your_tour_app.services.dto

class UserRegister(name: String, last_name: String, email: String, password: String, password_confirmation: String) {
    private var email = ""
    private var password = ""
    private var name = ""
    private var last_name = ""
    private var password_confirmation = ""

    init {
        this.email = email
        this.password = password
        this.name = name
        this.last_name = last_name
        this.password_confirmation = password_confirmation
    }
}