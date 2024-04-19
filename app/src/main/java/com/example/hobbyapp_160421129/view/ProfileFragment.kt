package com.example.hobbyapp_160421129.view

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hobbyapp_160421129.R
import com.example.hobbyapp_160421129.databinding.FragmentProfileBinding
import com.example.hobbyapp_160421129.viewModel.UserViewModel
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private lateinit var viewModel: UserViewModel
    private lateinit var binding: FragmentProfileBinding

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
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val userAccount = requireActivity().getSharedPreferences("loginAccount", Context.MODE_PRIVATE)
        val id = userAccount.getString("id", "")
        val firstName = userAccount.getString("firstName", "")
        val lastName = userAccount.getString("lastName", "")
        val oldPassword = userAccount.getString("password", "")
        val photo = userAccount.getString("photo", "")

        Picasso.get().load(photo).into(binding.imgProfile)
        binding.textInputLayoutFirstName.editText?.setText(firstName)
        binding.textInputLayoutLastName.editText?.setText(lastName)

        binding.btnUpdate.setOnClickListener {
            if(oldPassword == binding.textInputLayoutOldPass.editText?.text.toString()){
                viewModel.fetchUpdate(id.toString(), binding.textInputLayoutFirstName.editText?.text.toString(), binding.textInputLayoutLastName.editText?.text.toString(), binding.textInputLayoutNewPass.editText?.text.toString())
                viewModel.userUpdateLD.observe(viewLifecycleOwner, Observer {
                    if(it == true)
                    {
                        binding.textInputLayoutOldPass.editText?.setText("")
                        binding.textInputLayoutNewPass.editText?.setText("")
                    }
                })
            }
        }


        //        binding.btnLogout.setOnClickListener {
//            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
//            sharedPreferences.edit().putBoolean("login", false).apply()
//            val logOut = sharedPreferences.edit()
//            logOut.remove("id")
//            logOut.apply()
//        }


    }
}