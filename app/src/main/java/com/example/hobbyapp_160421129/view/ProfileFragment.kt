package com.example.hobbyapp_160421129.view

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.hobbyapp_160421129.R
import com.example.hobbyapp_160421129.databinding.FragmentProfileBinding
import com.example.hobbyapp_160421129.model.Users
import com.example.hobbyapp_160421129.viewModel.UserViewModel
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment(), ButtonActionNavClickListener, ButtonClickListener, TextInputClickListener {
    private lateinit var viewModel: UserViewModel
    private lateinit var binding: FragmentProfileBinding
//    val userAccount = requireActivity().getSharedPreferences("loginAccount", Context.MODE_PRIVATE)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listener = this
        binding.navListener = this
        binding.inputListener = this

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.itemHome)
            }
        })

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
//        viewModel.userLoginLD.observe(viewLifecycleOwner, Observer {
//            binding.user = it
//        })

        val userAccount = requireActivity().getSharedPreferences("loginAccount", Context.MODE_PRIVATE)
//        val id = userAccount.getString("id", "")
        val firstName = userAccount.getString("firstName", "")
        val lastName = userAccount.getString("lastName", "")
        val email = userAccount.getString("email", "")
        val username = userAccount.getString("username", "")
        val oldPassword = userAccount.getString("password", "")
        val photo = userAccount.getString("photo", "")
        Log.d("Check", photo.toString())

        binding.user = Users(firstName.toString(), lastName.toString(), email.toString(), username.toString(), oldPassword.toString(), photo.toString())

//        Picasso.get().load(photo).into(binding.imgProfile)
//        binding.textInputLayoutFirstName.editText?.setText(firstName)
//        binding.textInputLayoutLastName.editText?.setText(lastName)

//        binding.btnUpdate.setOnClickListener {
//            if(oldPassword == binding.textInputLayoutOldPass.editText?.text.toString()){
//                viewModel.fetchUpdate(id.toString(), binding.textInputLayoutFirstName.editText?.text.toString(), binding.textInputLayoutLastName.editText?.text.toString(), binding.textInputLayoutNewPass.editText?.text.toString())
//                viewModel.userUpdateLD.observe(viewLifecycleOwner, Observer {
//                    if(it == true)
//                    {
//                        binding.textInputLayoutOldPass.editText?.setText("")
//                        binding.textInputLayoutNewPass.editText?.setText("")
//                        Toast.makeText(requireContext(), "Update Success", Toast.LENGTH_SHORT).show()
//                    }
//                })
//            }
//        }

//        binding.btnLogout.setOnClickListener {
//            userAccount.edit().clear().apply()
//            val action = ProfileFragmentDirections.actionProfileLoginFragment()
//            Navigation.findNavController(view).navigate(action)
//        }

    }

    override fun onButtonClick(v: View) {
        val userAccount = requireActivity().getSharedPreferences("loginAccount", Context.MODE_PRIVATE)
        val oldPassword = userAccount.getString("password", "")
        val newPassword = binding.textInputLayoutNewPass.editText?.text.toString()
        val inputOldPassword = binding.textInputLayoutOldPass.editText?.text.toString()
        val id = userAccount.getString("id", "")

        if (inputOldPassword.isNotEmpty() && newPassword.isNotEmpty()){
            if(oldPassword == inputOldPassword){

                viewModel.fetchUpdate(binding.textInputLayoutFirstName.editText?.text.toString(), binding.textInputLayoutLastName.editText?.text.toString(), newPassword, id.toString().toInt())
//            viewModel.fetchUpdate(id.toString(), binding.textInputLayoutFirstName.editText?.text.toString(), binding.textInputLayoutLastName.editText?.text.toString(), binding.textInputLayoutNewPass.editText?.text.toString())
                viewModel.userUpdateLD.observe(viewLifecycleOwner, Observer {
                    if(it == true)
                    {
                        binding.textInputLayoutOldPass.editText?.setText("")
                        binding.textInputLayoutNewPass.editText?.setText("")
                        Toast.makeText(requireContext(), "Update Success", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        } else{
            if(inputOldPassword.isEmpty()){
                binding.textInputLayoutOldPass.error = "Your current password are required fields."
            }
            if(newPassword.isEmpty()){
                binding.textInputLayoutNewPass.error = "New password are required fields."
            }
        }
    }

    override fun onButtonActionNavClick(v: View) {
        val userAccount = requireActivity().getSharedPreferences("loginAccount", Context.MODE_PRIVATE)
        userAccount.edit().clear().apply()
        val action = ProfileFragmentDirections.actionProfileLoginFragment()
        Navigation.findNavController(v).navigate(action)
    }

    override fun onInputClick(v: View) {
        if(v.tag == "inputOldPass"){
            binding.textInputLayoutOldPass.error = null
            binding.textInputLayoutOldPass.isErrorEnabled = false
        }
        if(v.tag == "inputNewPass"){
            binding.textInputLayoutNewPass.error = null
            binding.textInputLayoutNewPass.isErrorEnabled = false
        }
    }
}