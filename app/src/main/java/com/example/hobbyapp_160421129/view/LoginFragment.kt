package com.example.hobbyapp_160421129.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.hobbyapp_160421129.R
import com.example.hobbyapp_160421129.databinding.FragmentLoginBinding
import com.example.hobbyapp_160421129.viewModel.UserViewModel
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.transition.Visibility
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment(), ButtonClickListener, ButtonActionNavClickListener, TextInputClickListener {
    private lateinit var viewModel: UserViewModel
    private lateinit var binding: FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.listener = this
        binding.navListener = this
        binding.inputListener = this

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.loginFragment)
            }
        })

//        binding.btnRegister.setOnClickListener {
//            val action = LoginFragmentDirections.actionRegisterFragment()
//            Navigation.findNavController(it).navigate(action)
//        }

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

//        binding.btnLogin.setOnClickListener {
//
//            var username = binding.txtInputUsername.editText?.text.toString()
//            var password = binding.textInputLayoutPassword.editText?.text.toString()
//
//            if (username.isNotEmpty() && password.isNotEmpty()) {
//                viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
//                viewModel.fetchLogin(username, password)
//
//                viewModel.checkLoginLD.observe(viewLifecycleOwner, Observer {success->
//                    if(success) {
//                        val saveLogin = requireActivity().getSharedPreferences("loginAccount", Context.MODE_PRIVATE)
//                        var editor = saveLogin.edit()
//                        viewModel.userLoginLD.value?.let { userLogin->
//                            editor.putString("id", userLogin.id)
//                            editor.putString("firstName", userLogin.first_name)
//                            editor.putString("lastName", userLogin.last_name)
//                            editor.putString("email", userLogin.email)
//                            editor.putString("username", userLogin.username)
//                            editor.putString("password", userLogin.password)
//                            editor.putString("photo", userLogin.photo)
//                            editor.apply()
//                        }
//                        val action = LoginFragmentDirections.actionHomeFragment()
//                        Navigation.findNavController(view).navigate(action)
//                        Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
//                    }else{
//                        Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
//                    }
//                })
//            }else{
//                Toast.makeText(requireContext(), "Please enter correct username and password", Toast.LENGTH_SHORT).show()
//            }
//
//        }
    }

    override fun onButtonClick(v: View) {

        var username = binding.txtInputUsername.editText?.text.toString()
        var password = binding.textInputLayoutPassword.editText?.text.toString()

        if (username.isNotEmpty() && password.isNotEmpty()) {
            viewModel.fetchLogin(username, password)

            viewModel.checkLoginLD.observe(viewLifecycleOwner, Observer {success->
                if(success) {
                    val saveLogin = requireActivity().getSharedPreferences("loginAccount", Context.MODE_PRIVATE)
                    var editor = saveLogin.edit()
                    viewModel.userLoginLD.value?.let { userLogin->
                        editor.putString("id", userLogin.uuid.toString())
                        editor.putString("firstName", userLogin.first_name)
                        editor.putString("lastName", userLogin.last_name)
                        editor.putString("email", userLogin.email)
                        editor.putString("username", userLogin.username)
                        editor.putString("password", userLogin.password)
                        editor.putString("photo", userLogin.photo)
                        editor.apply()
                    }
                    val action = LoginFragmentDirections.actionHomeFragment()
                    Navigation.findNavController(v).navigate(action)
                    Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            if(username.isEmpty()){
                binding.txtInputUsername.error = "Username are required fields."
            }
            if(password.isEmpty()){
                binding.textInputLayoutPassword.error = "Password are required fields."
            }
        }
    }

    override fun onButtonActionNavClick(v: View) {
        val action = LoginFragmentDirections.actionRegisterFragment()
        Navigation.findNavController(v).navigate(action)
    }

    override fun onInputClick(v: View) {
        if(v.tag == "inputUsername"){
            binding.txtInputUsername.error = null
            binding.txtInputUsername.isErrorEnabled = false
        }
        if(v.tag == "inputPassword"){
            binding.textInputLayoutPassword.error = null
            binding.textInputLayoutPassword.isErrorEnabled = false
        }
    }

}