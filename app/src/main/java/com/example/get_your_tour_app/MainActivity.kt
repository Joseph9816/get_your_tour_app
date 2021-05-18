package com.example.get_your_tour_app

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.get_your_tour_app.services.apiQueries
import com.example.get_your_tour_app.services.dto.UserDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
                setOf(
                        R.id.navigation_explore,
                        R.id.navigation_favorites,
                        R.id.navigation_reservations,
                        R.id.navigation_profile
                )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    fun showDatePickerDialog1(view: View) {
        val datePicker = DatePickerFragment{ day, month, year -> onDateSelectedStart(day, month, year, findViewById(R.id.startDate)) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    fun showDatePickerDialog2(view: View) {
        val start: EditText = findViewById(R.id.startDate)
        val text = start.text
        System.out.println("Text of startDate: $text")
        if(!text.isBlank()){
            val datePicker = DatePickerFragment{ day, month, year -> onDateSelectedEnd(day, month, year, findViewById(R.id.endDate))}
            datePicker.show(supportFragmentManager, "datePicker")
        }
        else{
            simpleAlert("Accept", "Select the start date first", "Date error")
        }
    }

    private fun onDateSelectedStart(day: Int, month: Int, year: Int, date: EditText) {
        date.setText(" $day/$month/$year")
        val endDate: EditText = findViewById(R.id.endDate)
        val text = endDate.text
        if(verifyEnd("$day/$month/$year", text.toString()) || text.equals("")) {
            date.setText("$day/$month/$year")
        } else {
            simpleAlert("Accept", "The end date cannot be before the start date", "Date error")
            date.setText("")
        }
    }

    private fun onDateSelectedEnd(day: Int, month: Int, year: Int, date: EditText) {
        val startDate: EditText = findViewById(R.id.startDate)
        val text = startDate.text
        if(verifyEnd(text.toString(), "$day/$month/$year")) {
            date.setText("$day/$month/$year")
        } else {
            simpleAlert("Accept", "The end date cannot be before the start date", "Date error")
            date.setText("")
        }
    }

    private fun simpleAlert(text: String, message: String, title: String) {
        var myBuild: AlertDialog.Builder = AlertDialog.Builder(this)
        myBuild.setMessage(message)
        myBuild.setTitle(title)
        myBuild.setPositiveButton(text, null)

        var dialog: AlertDialog = myBuild.create()
        dialog.show()
    }

    private fun verifyEnd(startDate: String, endDate: String): Boolean {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        var timeInMillisecondsStart = System.currentTimeMillis() - 1000
        var timeInMillisecondsEnd = System.currentTimeMillis() - 1000
        try {
            var mDate: Date = sdf.parse(startDate)
            timeInMillisecondsStart = mDate.time
            mDate = sdf.parse(endDate)
            timeInMillisecondsEnd = mDate.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return timeInMillisecondsStart <= timeInMillisecondsEnd
    }
}