package com.example.get_your_tour_app.ui.register

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.AutoText
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.get_your_tour_app.databinding.FragmentRegisterBinding
import com.example.get_your_tour_app.services.apiQueries
import com.example.get_your_tour_app.services.dto.UserDto
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.regex.Pattern

private const val TAG = "noOverride"
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private var emailStatus = false
    private var pass1 = false
    private var passMatch = false
    private var sharedPreferences: SharedPreferences? = null
    //private var BASE_URL = "http://7d2b90331d83.ngrok.io/GetYourTour_API/public/api/getyourtour"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.EmailAddress.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.i(TAG, "beforeTextChanged")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(binding.EmailAddress.text.toString()).matches()){
                    emailStatus = true

                }else{
                    emailStatus = false
                    binding.EmailAddress.setError("Invalid Email")
                }
            }

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "beforeTextChanged")
            }

        })
        binding.editTextTextPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(passValidate(binding.editTextTextPassword.text.toString())){
                    pass1 = true
                }else{
                    pass1 = false
                    binding.editTextTextPassword.setError("the password should have 1 Upercase, 1 undercase, one number and one character")
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        binding.editTextTextPassword2.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(binding.editTextTextPassword.text.toString().equals(binding.editTextTextPassword2.text.toString())){
                    passMatch = true
                }else{
                    passMatch = false
                    binding.editTextTextPassword2.setError("No password matched")
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

//donde el fagmento ya esta visible y se puede usar
    override fun onStart() {
        super.onStart()
        Log.i("Register", "Entro al onstart")
        binding.bRegister.setOnClickListener {

            if (binding.editTextTextPersonName.length() > 0 && binding.editTextTextPersonName2.length() > 0 && pass1 && emailStatus && passMatch) {
                binding.bRegister.setText("User Registered")
            } else {
                binding.bRegister.setText("some inputs are empty")
            }
        }

        sharedPreferences = activity?.getSharedPreferences("GETYOURTOURPREFERENCES", Context.MODE_PRIVATE)

        var editor = sharedPreferences?.edit()
        editor?.putString("user_name", "Joseph")
        editor?.putString("user_lastname", "Joseph")
        editor?.putString("user_id", "Joseph")
        editor?.putString("user_email", "Joseph")
        editor?.putString("user_logged", "true")
        editor?.commit()

        /*val service = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiQueries::class.java)

        service.storeUser().enqueue(object : Callback<List<UserDto>> {
            override fun onFailure(call: Call<List<UserDto>>, t: Throwable) {
                Log.d("TAG_", "An error in api query")
            }

            override fun onResponse(call: Call<List<UserDto>>, response: Response<List<UserDto>>) {
                Log.d("TAG_", response.body().toString())
            }

        })*/
    }

    private fun passValidate(text: String?): Boolean{
        var p = Pattern.compile("^(?=.*\\d)(?=.*[\\u0021-\\u002b\\u003c-\\u0040])(?=.*[A-Z])(?=.*[a-z])\\S{8,16}\$")
        var m = p.matcher(text)
        return m.matches()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}