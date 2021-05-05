package com.example.get_your_tour_app.ui.profile

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
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.get_your_tour_app.R
import com.example.get_your_tour_app.databinding.FragmentProfileBinding
import java.util.regex.Pattern

private const val TAG = "noOverride"
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var email = true
    private var pass = true


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
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
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(binding.EmailAddress.text.toString()).matches()){
                    //binding.button.isEnabled = true
                    email = false
                }else{
                    //binding.button.isEnabled = false
                        email = true
                    binding.EmailAddress.setError("Invalid Email")
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
                if(binding.Password.length() >= 8){
                    pass = false
                    binding.button.isEnabled = true
                }
                if(binding.Password.length() < 8 ){
                    pass = true
                    binding.button.isEnabled = false
                    binding.Password.setError("length of password is to short")
                }

            }

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "beforeTextChanged")

            }

        })
        fun validateButton(){
            binding.button.isEnabled = !email && !pass
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}