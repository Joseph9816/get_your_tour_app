package com.example.get_your_tour_app.ui.explore

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.get_your_tour_app.Constants
import com.example.get_your_tour_app.RecycleAdapter
import com.example.get_your_tour_app.databinding.FragmentExploreBinding


class  ExploreFragment : Fragment() {

    private lateinit var viewModel: ExploreViewModel
    private var _binding: FragmentExploreBinding? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecycleAdapter.ViewHolder>? = null
    private val binding get() = _binding!!
    private var sharedPreferences: SharedPreferences? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(ExploreViewModel::class.java)
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        sharedPreferences = activity?.getSharedPreferences("GETYOURTOURPREFERENCES", Context.MODE_PRIVATE)
        val userId = sharedPreferences?.getString("user_id", "-1")
        if (userId != null) {
            Constants.UserId = userId.toIntOrNull()!!
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ExploreViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getTokenValue()
        viewModel.getToken().observe(viewLifecycleOwner, Observer {
            Log.d("TAG_", it.toString())
        })
        val handler = Handler ()
        handler.postDelayed(Runnable {
            val recyclerView = binding.recycleView
            viewModel.getToursList()
            viewModel.getTours().observe(viewLifecycleOwner, Observer {

                Log.d("TAG_", it.toString())

                recyclerView.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = RecycleAdapter(it)

                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = adapter
                }
            })
        }, 2500)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}