package com.example.hobbyapp_160421129.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
import com.example.hobbyapp_160421129.R
import com.example.hobbyapp_160421129.databinding.FragmentRegisterBinding
import com.example.hobbyapp_160421129.model.Users
import com.example.hobbyapp_160421129.viewModel.UserViewModel
import com.google.android.material.navigation.NavigationView
import kotlin.math.log

class RegisterFragment : Fragment(), ButtonClickListener, TextInputClickListener {
    private lateinit var viewModel: UserViewModel
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.listener = this
        binding.inputListener = this

        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).findViewById<NavigationView>(R.id.navView).visibility = View.GONE

        binding.user = Users("", "", "", "", "", "")

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)



//        binding.btnOk.setOnClickListener {
//            var firstName = binding.textInputLayoutFirst.editText?.text.toString()
//            var lastName = binding.textInputLayoutLast.editText?.text.toString()
//            var email = binding.textInputLayoutEmail.editText?.text.toString()
//            var username = binding.textInputLayoutUsername.editText?.text.toString()
//            var password = binding.textInputLayoutPass.editText?.text.toString()
//            var rePass = binding.textInputLayoutRePass.editText?.text.toString()
//            var photo = binding.textInputLayoutPhoto.editText?.text.toString()
//
//
//            //Check if password and retype password is same
//            if(password == rePass)
//            {
//                viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
//                viewModel.fetchRegister(firstName, lastName, email, username, password, photo)
//
//                viewModel.userRegistLD.observe(viewLifecycleOwner, Observer {
//                    if(it == true){
//                        val action = RegisterFragmentDirections.actionLoginFragment()
//                        Navigation.findNavController(view).navigate(action)
//                    }
//                })
//            }
//
//        }
    }

    override fun onButtonClick(v: View) {

        var firstName = binding.textInputLayoutFirst.editText?.text.toString()
        var lastName = binding.textInputLayoutLast.editText?.text.toString()
        var email = binding.textInputLayoutEmail.editText?.text.toString()
        var username = binding.textInputLayoutUsername.editText?.text.toString()
        var password = binding.textInputLayoutPass.editText?.text.toString()
        var rePass = binding.textInputLayoutRePass.editText?.text.toString()
        var photo = binding.textInputLayoutPhoto.editText?.text.toString()

//        Log.d("Check", binding.user!!.password)
        if(firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty() && rePass.isNotEmpty() && photo.isNotEmpty()){

            if (password == rePass) {
                binding.user = Users(firstName, lastName, email, username, password, photo)
                viewModel.fetchRegister(binding.user!!)
                Toast.makeText(v.context, "Registration successful!", Toast.LENGTH_SHORT).show()

                val action = RegisterFragmentDirections.actionLoginFragment()
                Navigation.findNavController(v).navigate(action)
            } else {
                binding.textInputLayoutPass.error = "Password does not match."
                binding.textInputLayoutRePass.error = "Password does not match."

            }
        } else{
            if(firstName.isEmpty()){
                binding.textInputLayoutFirst.error = "First Name are required fields."
            }
            if(lastName.isEmpty()){
                binding.textInputLayoutLast.error = "Last Name are required fields."
            }
            if(email.isEmpty()){
                binding.textInputLayoutEmail.error = "Email are required fields."
            }
            if(username.isEmpty()){
                binding.textInputLayoutUsername.error = "Username are required fields."
            }
            if(password.isEmpty()){
                binding.textInputLayoutPass.error = "Password are required fields."
            }
            if(rePass.isEmpty()){
                binding.textInputLayoutRePass.error = "Retype Password are required fields."
            }
            if(photo.isEmpty()){
                binding.textInputLayoutPhoto.error = "Photo are required fields."
            }
        }
    }

    override fun onInputClick(v: View) {
        if(v.tag == "inputUsername"){
            binding.textInputLayoutUsername.error = null
            binding.textInputLayoutUsername.isErrorEnabled = false
        }
        if(v.tag == "inputPassword"){
            binding.textInputLayoutPass.error = null
            binding.textInputLayoutPass.isErrorEnabled = false
        }
        if(v.tag == "txtFirst"){
            binding.textInputLayoutFirst.error = null
            binding.textInputLayoutFirst.isErrorEnabled = false
        }
        if(v.tag == "inputLName"){
            binding.textInputLayoutLast.error = null
            binding.textInputLayoutLast.isErrorEnabled = false
        }
        if(v.tag == "inputEmail"){
            binding.textInputLayoutEmail.error = null
            binding.textInputLayoutEmail.isErrorEnabled = false
        }
        if(v.tag == "inputPhoto"){
            binding.textInputLayoutPhoto.error = null
            binding.textInputLayoutPhoto.isErrorEnabled = false
        }
        if(v.tag == "inputRetypePassword"){
            binding.textInputLayoutRePass.error = null
            binding.textInputLayoutRePass.isErrorEnabled = false
        }
    }


}