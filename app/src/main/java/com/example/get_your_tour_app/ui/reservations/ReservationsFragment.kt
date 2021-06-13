package com.example.get_your_tour_app.ui.reservations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.get_your_tour_app.databinding.FragmentReservationsBinding

class ReservationsFragment : Fragment() {

    private lateinit var viewModel: ReservationsViewModel
    private lateinit var _binding: FragmentReservationsBinding
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReservationsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReservationsViewModel::class.java)
        _binding.textReservations.text = viewModel.getMainText()
    }
}