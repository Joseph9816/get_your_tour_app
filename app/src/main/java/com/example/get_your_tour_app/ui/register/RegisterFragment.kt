package com.example.get_your_tour_app.ui.register

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.get_your_tour_app.R
import com.example.get_your_tour_app.Utils.Utils
import com.example.get_your_tour_app.databinding.FragmentRegisterBinding
import com.example.get_your_tour_app.services.UserService
import com.example.get_your_tour_app.services.dto.UserDto
import com.example.get_your_tour_app.services.dto.UserRegister
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
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
                    binding.EmailAddress.error = "Invalid Email"
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
                    binding.editTextTextPassword.error = getString(R.string.password_format)
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
                    binding.editTextTextPassword2.error = "No password matched"
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
                    binding.editTextTextPersonName.error = "Required"
                    validateButton()
                }else if(binding.editTextTextPersonName.length() > 50){
                    name = true
                    binding.editTextTextPersonName.error = "50 characters maximum"
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
                    binding.editTextTextPersonName2.error = "Required"
                    validateButton()
                }else if(binding.editTextTextPersonName2.length() > 50){
                    last_name = true
                    binding.editTextTextPersonName2.error = "50 characters maximum"
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

        binding.bRegister.setOnClickListener{
            Toast.makeText(activity?.applicationContext, "Registering UserService", Toast.LENGTH_SHORT).show()

            viewModel.setName(binding.editTextTextPersonName.text.toString())
            viewModel.setLastNames(binding.editTextTextPersonName2.text.toString())
            viewModel.setEmail(binding.EmailAddress.text.toString())
            viewModel.setPassword(binding.editTextTextPassword.text.toString())
            viewModel.setPasswordConfirmation(binding.editTextTextPassword2.text.toString())

            viewModel.register()
            viewModel.getUser().observe(viewLifecycleOwner, { user ->
                val length = user.count()
                if (length > 0) {
                    if (user[0].status == "1") {
                        Log.d("TAG_", "Hay datos de usuario")
                        Toast.makeText(activity?.applicationContext, "User Registered", Toast.LENGTH_SHORT).show()
                        it.findNavController().navigate(R.id.action_navigation_register_to_navigation_profile)
                    } else {
                        Log.d("TAG_", "No hay datos de usuario")
                        Toast.makeText(activity?.applicationContext, "Can't register user", Toast.LENGTH_SHORT).show()
                    }
                }
            })
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