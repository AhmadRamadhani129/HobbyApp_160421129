package com.example.hobbyapp_160421129.viewModel

import android.app.Application
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hobbyapp_160421129.model.Users
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserViewModel(application: Application):AndroidViewModel(application) {
    var userLoginLD = MutableLiveData<Users>()
    val userRegistLD = MutableLiveData<Boolean>()
    val userUpdateLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue:RequestQueue ?= null

    fun fetchRegister(firstName: String, lastName: String, email: String, username: String, password: String, photo: String){
//        userLoadErrorLD.value = false
//        userLoadLD.value = true

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/hobbyApp/register.php"

        val stringRequest = object : StringRequest(
            Method.POST, url,
            {response->
                userRegistLD.value = true
                Log.d("Register", "Result: ${response}")
            },
            {
                userRegistLD.value = false
                Log.d("Register", it.toString())
            }

        )
        {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["firstName"] = firstName
                params["lastName"] = lastName
                params["email"] = email
                params["username"] = username
                params["password"] = password
                params["photo"] = photo
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun fetchLogin(username: String, password: String){

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/hobbyApp/login.php"

        val stringRequest = object : StringRequest(
            Method.POST, url,
            {response->
                userLoginLD.value = Gson().fromJson(response, Users::class.java)

                Log.d("Login", "Result: ${response}")
            },
            {
                Log.d("Login", it.toString())
            }

        )
        {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["username"] = username
                params["password"] = password
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun fetchUpdate(id:String, firstName: String, lastName: String, password: String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/hobbyApp/updateUser.php"

        val stringRequest = object : StringRequest(
            Method.POST, url,{response->
                userUpdateLD.value = true
                Log.d("Update", "Result: ${response}")
            },
            {
                userUpdateLD.value = false
                Log.d("Update", it.toString())
            }
        )
        {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["id"] = id
                params["firstName"] = firstName
                params["lastName"] = lastName
                params["password"] = password
                return params
            }
        }
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}