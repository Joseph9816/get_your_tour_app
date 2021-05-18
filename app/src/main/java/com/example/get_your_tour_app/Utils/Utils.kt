package com.example.get_your_tour_app.Utils

import java.util.regex.Pattern

class Utils {
     fun passValidate(text: String?): Boolean{
        val p = Pattern.compile("^(?=.*\\d)(?=.*[\\u0021-\\u002b\\u003c-\\u0040])(?=.*[A-Z])(?=.*[a-z])\\S{8,16}\$")
        val m = p.matcher(text)
        return m.matches()
    }
}