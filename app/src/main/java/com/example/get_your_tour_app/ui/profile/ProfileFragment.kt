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
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.get_your_tour_app.R
import com.example.get_your_tour_app.RecycleAdapter
import com.example.get_your_tour_app.Utils.Utils
import com.example.get_your_tour_app.databinding.FragmentProfileBinding
import com.example.get_your_tour_app.services.UserService
import com.example.get_your_tour_app.services.dto.UserDto
import com.example.get_your_tour_app.services.dto.UserLog
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "noOverride"
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var email = true
    private var pass = true
    private var sharedPreferences: SharedPreferences? = null
    private lateinit var viewModel: ProfileViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
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
                    email = false
                    viewModel.setEmail(binding.EmailAddress.text.toString())
                    validateButton()
                }else{
                    email = true
                    validateButton()
                    binding.EmailAddress.error = getString(R.string.email_error)
                }

            }

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChanged")

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
                    viewModel.setPassword(binding.Password.text.toString())
                    validateButton()
                    //binding.button.isEnabled = true
                }
                if(binding.Password.length() < 8 ){
                    pass = true
                    //binding.button.isEnabled = false
                    validateButton()
                    binding.Password.error = getString(R.string.password_format)
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
            viewModel.setEmail(binding.EmailAddress.text.toString())
            viewModel.setPassword(binding.Password.text.toString())
            viewModel.login()
            viewModel.getUser().observe(viewLifecycleOwner, Observer { user ->

                val length = user.count()
                if(length > 0) {
                    Log.d("TAG_", user.toString())
                    user[0].let {
                        if (it.status == "1") {
                            val editor = sharedPreferences?.edit()
                            editor?.putString("user_logged", "true")
                            editor?.putString("user_id", it.id.toString())
                            editor?.putString("user_name", it.name)
                            editor?.putString("user_last_name", it.last_name)
                            editor?.putString("user_email", it.email)
                            editor?.apply()
                        } else if (it.status == "2") {
                            val editor = sharedPreferences?.edit()
                            editor?.putString("user_logged", "false")
                            editor?.apply()
                            alert(
                                getString(R.string.alert_button_name),
                                getString(R.string.login_error),
                                getString(R.string.warning)
                            )
                        } else {
                            val editor = sharedPreferences?.edit()
                            editor?.putString("user_logged", "false")
                            editor?.apply()
                            alert(
                                getString(R.string.alert_button_name),
                                getString(R.string.api_error),
                                getString(R.string.warning)
                            )
                        }
                    }
                    if (user[0].status == "1") {
                        it.findNavController().navigate(R.id.action_navigation_profile_to_navigation_explore)
                    }
                }
            })
        }

        binding.logOut.setOnClickListener(){
            sharedPreferences = activity?.getSharedPreferences("GETYOURTOURPREFERENCES", Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.putString("user_logged", "false")
            editor?.putString("user_id", "")
            editor?.putString("user_name", "")
            editor?.putString("user_last_name", "")
            editor?.putString("user_email", "")
            editor?.apply()
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