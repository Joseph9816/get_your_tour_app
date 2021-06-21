package com.example.get_your_tour_app.services.dto

class SearchDto(start_date: String, end_date: String, place: String) {
    private var start_date = ""
    private var end_date = ""
    private var place = ""

    init {
        this.start_date = start_date
        this.end_date = end_date
        this.place = place
    }
}