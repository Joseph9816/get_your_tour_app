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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.get_your_tour_app.R
import com.example.get_your_tour_app.Utils.Utils
import com.example.get_your_tour_app.databinding.FragmentRegisterBinding
import com.example.get_your_tour_app.services.Token
import com.example.get_your_tour_app.services.User
import com.example.get_your_tour_app.services.apiQueries
import com.example.get_your_tour_app.services.dto.TokenDto
import com.example.get_your_tour_app.services.dto.TokenValue
import com.example.get_your_tour_app.services.dto.UserDto
import com.example.get_your_tour_app.services.dto.UserRegister
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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
    private var email = true
    private var pass = true
    private var name = true
    private var last_name = true
    private var pass2 = true
    private var sharedPreferences: SharedPreferences? = null
    private var BASE_URL = "http://9aec1ae89e41.ngrok.io/api/getyourtour/"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        validateButton()
        binding.EmailAddress.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(binding.EmailAddress.text.toString()).matches()){
                    emailStatus = true
                    email = false
                    validateButton()
                }else{
                    emailStatus = false
                    email = true
                    binding.EmailAddress.setError("Invalid Email")
                    validateButton()
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.editTextTextPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val util = Utils()
                if(util.passValidate(binding.editTextTextPassword.text.toString())){
                    pass1 = true
                    pass = false
                    validateButton()
                }else{
                    pass1 = false
                    pass = true
                    binding.editTextTextPassword.setError(getString(R.string.password_format))
                    validateButton()
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
                    pass2 = false
                    validateButton()
                }else{
                    passMatch = false
                    pass2 = true
                    binding.editTextTextPassword2.setError("No password matched")
                    validateButton()
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.editTextTextPersonName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(binding.editTextTextPersonName.length() == 0){
                    name = true
                    binding.editTextTextPersonName.setError("Required")
                    validateButton()
                }else if(binding.editTextTextPersonName.length() > 50){
                    name = true
                    binding.editTextTextPersonName.setError("50 characters maximum")
                    validateButton()
                }else{
                    name = false
                    validateButton()
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.editTextTextPersonName2.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(binding.editTextTextPersonName2.length() == 0){
                    last_name = true
                    binding.editTextTextPersonName2.setError("Required")
                    validateButton()
                }else if(binding.editTextTextPersonName2.length() > 50){
                    last_name = true
                    binding.editTextTextPersonName2.setError("50 characters maximum")
                    validateButton()
                }else{
                    last_name = false
                    validateButton()
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

    }

//donde el fagmento ya esta visible y se puede usar
    override fun onStart() {
        super.onStart()

        //se crea el servico para consumir la api
        binding.bRegister.setOnClickListener{
            Toast.makeText(activity?.applicationContext, "Registering User", Toast.LENGTH_SHORT).show()
            //se recupera el token almacenado
            sharedPreferences = activity?.getSharedPreferences("GETYOURTOURPREFERENCES", Context.MODE_PRIVATE)
            val token = sharedPreferences?.getString("api_token", "")

            //se crea el servicio
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val service = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(User::class.java)
            //se crea una clase con los datos de registro
            val user = UserRegister(binding.editTextTextPersonName.text.toString(), binding.editTextTextPersonName2.text.toString(), binding.EmailAddress.text.toString(), binding.editTextTextPassword.text.toString(), binding.editTextTextPassword2.text.toString())

            //se verifica si el token existe
            if (token != null) {
                service.storeUser(token, user).enqueue(object : Callback<List<UserDto>> {
                    override fun onResponse(call: Call<List<UserDto>>, response: Response<List<UserDto>>) {
                        Log.d("TAG_", "Entro al callback")
                        Log.d("TAG_", response.body().toString())
                        if(response.body() != null){
                            Toast.makeText(activity?.applicationContext, "User Registered", Toast.LENGTH_SHORT).show()
                            it.findNavController().navigate(R.id.action_navigation_register_to_navigation_profile)
                        }
                    }

                    override fun onFailure(call: Call<List<UserDto>>, t: Throwable) {
                        Log.d("TAG_", "An error in api query")
                        Log.d("TAG_", t.message.toString())
                    }
                })
            } else {
                Log.d("TAG_", "The token doesn't exist")
            }
        }//finaliza el onclick listener del bRegister
    }



    fun validateButton(){
        binding.bRegister.isEnabled = !name && !last_name && !pass && !pass2 && !email
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}