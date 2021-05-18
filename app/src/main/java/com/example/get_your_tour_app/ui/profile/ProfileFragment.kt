package com.example.get_your_tour_app.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.get_your_tour_app.R
import com.example.get_your_tour_app.Utils.Utils
import com.example.get_your_tour_app.databinding.FragmentProfileBinding
import com.example.get_your_tour_app.services.User
import com.example.get_your_tour_app.services.dto.UserDto
import com.example.get_your_tour_app.services.dto.UserLog
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.regex.Pattern

private const val TAG = "noOverride"
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var email = true
    private var pass = true
    private var sharedPreferences: SharedPreferences? = null
    private var BASE_URL = "http://aa6a0059b9d9.ngrok.io/api/getyourtour/"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        sharedPreferences = activity?.getSharedPreferences("GETYOURTOURPREFERENCES", Context.MODE_PRIVATE)
        val logged = sharedPreferences?.getString("user_logged", "false")
        if(logged.equals("true")){
            binding.constraintProfile.visibility = View.VISIBLE
            binding.constraintLogIn.visibility = View.GONE
            binding.userName.text = sharedPreferences?.getString("user_name", "")
        }else{
            binding.constraintProfile.visibility = View.GONE
            binding.constraintLogIn.visibility = View.VISIBLE
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button3.setOnClickListener {
            it.findNavController().navigate(R.id.action_navigation_profile_to_navigation_register)
        }

        binding.EmailAddress.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.i(TAG, "beforeTextChanged")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (Patterns.EMAIL_ADDRESS.matcher(binding.EmailAddress.text.toString()).matches()){
                    //binding.button.isEnabled = true
                    email = false
                    validateButton()
                }else{
                    //binding.button.isEnabled = false
                    email = true
                    validateButton()
                    binding.EmailAddress.setError(getString(R.string.email_error))
                }

            }

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChanged")
                /*if(binding.EmailAddress.length()>0){
                    binding.button.isEnabled = true
                }
                if(binding.EmailAddress.length() <= 0){
                    binding.button.isEnabled = false
                }*/

            }

        })
        binding.Password.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.i(TAG, "beforeTextChanged")

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val util = Utils()
                if(util.passValidate(binding.Password.text.toString())){
                    pass = false
                    validateButton()
                    //binding.button.isEnabled = true
                }
                if(binding.Password.length() < 8 ){
                    pass = true
                    //binding.button.isEnabled = false
                    validateButton()
                    binding.Password.setError(getString(R.string.password_format))
                }

            }

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "beforeTextChanged")

            }

        })


    }
    override fun onStart(){
        super.onStart()

        binding.button.setOnClickListener(){
            sharedPreferences = activity?.getSharedPreferences("GETYOURTOURPREFERENCES", Context.MODE_PRIVATE)
            val token = sharedPreferences?.getString("api_token", "")
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val service = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(User::class.java)
            val log = UserLog(binding.EmailAddress.text.toString(), binding.Password.text.toString())
            if (token != null) {
                service.login(token, log).enqueue(object : Callback<List<UserDto>>{
                    override fun onResponse(call: Call<List<UserDto>>, response: Response<List<UserDto>>) {
                        Log.d("TAG_", "Entro al callback")
                        Log.d("TAG_", response.body().toString())
                        if (response.body() != null) {
                            Log.d("TAG_", "Entro al if")
                            response.body()?.get(0)?.let {
                                sharedPreferences = activity?.getSharedPreferences("GETYOURTOURPREFERENCES", Context.MODE_PRIVATE)
                                val editor = sharedPreferences?.edit()
                                editor?.putString("user_logged", "true")
                                editor?.putString("user_id", it.id.toString())
                                editor?.putString("user_name", it.name)
                                editor?.putString("user_last_name", it.last_name)
                                editor?.putString("user_email", it.email)
                                editor?.commit()

                            }
                            it.findNavController().navigate(R.id.action_navigation_profile_to_navigation_explore)
                                /*if (it.status.equals('0')) {
                                    sharedPreferences = activity?.getSharedPreferences(
                                        "GETYOURTOURPREFERENCES",
                                        Context.MODE_PRIVATE
                                    )

                                    val editor = sharedPreferences?.edit()
                                    editor?.putString("user_logged", "true")
                                    editor?.putString("user_id", it.id.toString())
                                    editor?.putString("user_name", it.name)
                                    editor?.putString("user_last_name", it.last_name)
                                    editor?.putString("user_email", it.email)
                                    editor?.commit()

                                } else if (it.status.equals('2')) {
                                    sharedPreferences = activity?.getSharedPreferences(
                                        "GETYOURTOURPREFERENCES",
                                        Context.MODE_PRIVATE
                                    )

                                    val editor = sharedPreferences?.edit()
                                    editor?.putString("user_logged", "false")
                                    editor?.commit()

                                    simpleAlert(
                                        "Accept",
                                        R.string.login_error.toString(),
                                        R.string.warning.toString()
                                    )
                                } else {
                                    sharedPreferences = activity?.getSharedPreferences(
                                        "GETYOURTOURPREFERENCES",
                                        Context.MODE_PRIVATE
                                    )

                                    val editor = sharedPreferences?.edit()
                                    editor?.putString("user_logged", "false")
                                    editor?.commit()

                                    simpleAlert(
                                        "Accept",
                                        R.string.login_error.toString(),
                                        R.string.warning.toString()
                                    )
                                }
                            }*/
                        }else{

                            sharedPreferences = activity?.getSharedPreferences("GETYOURTOURPREFERENCES", Context.MODE_PRIVATE)

                            val editor = sharedPreferences?.edit()
                            editor?.putString("user_logged", "false")
                            editor?.commit()

                            //Toast.makeText(activity?.applicationContext, R.string.login_error, Toast.LENGTH_SHORT).show()
                            alert(getString(R.string.alert_button_name), getString(R.string.login_error), getString(R.string.warning))

                        }
                    }

                    override fun onFailure(call: Call<List<UserDto>>, t: Throwable) {
                        Log.d("TAG_", "An error in api query")
                        Log.d("TAG_", t.message.toString())
                    }

                })


            }
        }
        binding.logOut.setOnClickListener(){
            sharedPreferences = activity?.getSharedPreferences("GETYOURTOURPREFERENCES", Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.putString("user_logged", "false")
            editor?.putString("user_id", "")
            editor?.putString("user_name", "")
            editor?.putString("user_last_name", "")
            editor?.putString("user_email", "")
            editor?.commit()
            binding.constraintProfile.visibility = View.GONE
            binding.constraintLogIn.visibility = View.VISIBLE
        }


    }


    fun validateButton(){
        binding.button.isEnabled = !email && !pass
    }
    private fun alert(text: String, message: String, title: String){
        val alertDialog: AlertDialog? = activity?.let {val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(text, null)
                setMessage(message)
                setTitle(title)
            }
            builder.create()
        }
        if (alertDialog != null) {
            alertDialog.show()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}