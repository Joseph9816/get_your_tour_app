package com.example.get_your_tour_app.ui.register

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
import java.util.regex.Pattern

private const val TAG = "noOverride"
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private var emailStatus = false
    private var pass1 = false

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