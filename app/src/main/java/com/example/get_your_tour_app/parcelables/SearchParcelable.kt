package com.example.get_your_tour_app.parcelables

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchParcelable(
    val start_date: String,
    val end_date: String,
    val place: String
) : Parcelable