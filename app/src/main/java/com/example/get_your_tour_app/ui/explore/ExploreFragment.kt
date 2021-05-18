package com.example.get_your_tour_app.ui.explore

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.get_your_tour_app.R
import com.example.get_your_tour_app.RecycleAdapter
import com.example.get_your_tour_app.databinding.FragmentExploreBinding
import com.example.get_your_tour_app.services.Token
import com.example.get_your_tour_app.services.dto.TokenDto
import com.example.get_your_tour_app.services.dto.TokenValue
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class  ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecycleAdapter.ViewHolder>? = null
    private var sharedPreferences: SharedPreferences? = null
    private var BASE_URL = "http://ee84eb1b052a.ngrok.io/api/getyourtour/"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_explore, container, false)
        _binding = FragmentExploreBinding.inflate(inflater, container, false)

        var recycleView: RecyclerView = binding.recycleView;

        adapter = RecycleAdapter()
        layoutManager = LinearLayoutManager(view.context)
        recycleView.layoutManager = layoutManager
        recycleView.adapter = adapter

        //se crea el servico para consumir la api
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val service = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(Token::class.java)

        val token = TokenDto()
        service.getToken(token).enqueue(object : Callback<List<TokenValue>> {
            override fun onResponse(call: Call<List<TokenValue>>, response: Response<List<TokenValue>>) {
                Log.d("TAG_", "Entro al callback")
                Log.d("TAG_", response.body().toString())
                response.body()?.get(0)?.let {
                    Log.d("TAG_", it.token)
                    sharedPreferences = activity?.getSharedPreferences("GETYOURTOURPREFERENCES", Context.MODE_PRIVATE)

                    val editor = sharedPreferences?.edit()
                    editor?.putString("api_token", "Bearer " + it.token)
                    editor?.commit()
                }

                //se guarda el token de la api
                //val techTalkDto = response.body()?.get(0)
            }

            override fun onFailure(call: Call<List<TokenValue>>, t: Throwable) {
                Log.d("TAG_", "An error in api query")
                Log.d("TAG_", t.message.toString())
            }

        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}