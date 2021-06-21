package com.example.get_your_tour_app.ui.search

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.get_your_tour_app.Constants
import com.example.get_your_tour_app.R
import com.example.get_your_tour_app.RecycleAdapter
import com.example.get_your_tour_app.databinding.FragmentSearchBinding
import com.example.get_your_tour_app.ui.explore.ExploreViewModel

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecycleAdapter.ViewHolder>? = null
    private val binding get() = _binding!!
    private var sharedPreferences: SharedPreferences? = null
    private val args: SearchFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        sharedPreferences = activity?.getSharedPreferences("GETYOURTOURPREFERENCES", Context.MODE_PRIVATE)
        /*val userId = sharedPreferences?.getString("user_id", "-1")
        if (userId != null) {
            Constants.UserId = userId.toIntOrNull()!!
        }*/
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recycleView
        viewModel.getSpecifiedTours(args.search)
        viewModel.getTours().observe(viewLifecycleOwner, Observer {

            Log.d("TAG_Recibied", it.toString())

            recyclerView.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = RecycleAdapter(it)

                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = adapter
            }
            if (it.count() == 0) {
                binding.tEmpty.visibility = View.VISIBLE
                binding.recycleView.visibility = View.GONE
            } else {
                binding.tEmpty.visibility = View.GONE
                binding.recycleView.visibility = View.VISIBLE
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}