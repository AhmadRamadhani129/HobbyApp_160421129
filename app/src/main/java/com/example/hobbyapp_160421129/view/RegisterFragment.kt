package com.example.hobbyapp_160421129.view

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
import com.example.hobbyapp_160421129.databinding.FragmentRegisterBinding
import com.example.hobbyapp_160421129.viewModel.UserViewModel
import kotlin.math.log

class RegisterFragment : Fragment() {
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
        super.onViewCreated(view, savedInstanceState)
        binding.btnOk.setOnClickListener {
            var firstName = binding.textInputLayoutFirst.editText?.text.toString()
            var lastName = binding.textInputLayoutLast.editText?.text.toString()
            var email = binding.textInputLayoutEmail.editText?.text.toString()
            var username = binding.textInputLayoutUsername.editText?.text.toString()
            var password = binding.textInputLayoutPass.editText?.text.toString()
            var rePass = binding.textInputLayoutRePass.editText?.text.toString()
            var photo = binding.textInputLayoutPhoto.editText?.text.toString()


            //Check if password and retype password is same
            if(password == rePass)
            {
                viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
                viewModel.fetchRegister(firstName, lastName, email, username, password, photo)

                viewModel.userRegistLD.observe(viewLifecycleOwner, Observer {
                    if(it == true){
                        val action = RegisterFragmentDirections.actionLoginFragment()
                        Navigation.findNavController(view).navigate(action)
                    }
                })
            }

        }
    }


}