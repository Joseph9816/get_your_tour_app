package com.example.get_your_tour_app

import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.get_your_tour_app.R.id.recycleView
import kotlin.math.log

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            /*R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,*/ R.id.navigation_explore, R.id.navigation_favorites, R.id.navigation_reservations))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    fun showDatePickerDialog1(view: View) {

        val datePicker = DatePickerFragment{ day, month, year -> onDateSelected(day, month, year, findViewById(R.id.startDate)) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    fun showDatePickerDialog2(view: View) {
        val datePicker = DatePickerFragment{ day, month, year -> onDateSelected(day, month, year , findViewById(R.id.endDate)) }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun onDateSelected(day:Int, month:Int, year:Int, date: EditText) {
        date.setText(" $day/$month/$year")
    }
}