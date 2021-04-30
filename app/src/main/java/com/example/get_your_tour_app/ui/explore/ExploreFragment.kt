package com.example.get_your_tour_app.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.get_your_tour_app.R
import com.example.get_your_tour_app.databinding.FragmentExploreBinding

class  ExploreFragment : Fragment() {

    private lateinit var exploreViewModel: ExploreViewModel
    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        exploreViewModel =
            ViewModelProvider(this).get(ExploreViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_explore, container, false)
        val textView: TextView = root.findViewById(R.id.text_explore)
        exploreViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })/*

        val EdText: EditText = root.findViewById(R.id.startDate)
        EdText.setOnClickListener { showDatePickerDialog() }*/
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        binding.textExplore.text = "Selecciona el lugar y fechas que deseas buscar"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
/*
    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(supportFragmentManger, "datePicker")
    }

    fun onDateSelected(day:Int, month:Int, year:Int) {
        // startDate.setText("$day/$month/$year")
    }*/
}