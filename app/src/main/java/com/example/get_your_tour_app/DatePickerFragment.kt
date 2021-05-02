package com.example.get_your_tour_app

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DatePickerFragment(val listener: (day:Int, month:Int, year:Int) -> Unit): DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener(dayOfMonth, month, year)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val dPickerDialog = DatePickerDialog(activity as Context,this, year, month, day)

        convertDateToMillis("05/05/2021")?.let { dPickerDialog.datePicker.minDate = it }
        return dPickerDialog
    }

    //givenDateString must be defined in format dd/MM/yyyy
    private fun convertDateToMillis(givenDateString: String): Long? {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        var timeInMilliseconds = System.currentTimeMillis() - 1000
        try {
            val mDate: Date = sdf.parse(givenDateString)
            timeInMilliseconds = mDate.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return timeInMilliseconds
    }
}