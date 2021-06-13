package com.example.get_your_tour_app

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment

class GetTokenFragment : Fragment() {
    private var sharedPreferences: SharedPreferences? = null
    var token: String = ""

    init {
        sharedPreferences = activity?.getSharedPreferences("GETYOURTOURPREFERENCES", Context.MODE_PRIVATE)
        token = sharedPreferences?.getString("api_token", "").toString()
    }

    fun getUserId(): String {
        return sharedPreferences?.getString("user_id", "-1").toString()
    }

    fun setTokenn(tok: String) {
        val editor = sharedPreferences?.edit()
        editor?.putString("api_token", "Bearer " + tok)
        editor?.commit()
        token = sharedPreferences?.getString("api_token", "").toString()
    }
}