package com.example.hobbyapp_160421129.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.hobbyapp_160421129.model.News
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HomeViewModel(application: Application):AndroidViewModel(application) {
    var homesLD =MutableLiveData<ArrayList<News>>()
    val homesLoadErrorLD =MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val TAG = "volleyTag"
    private var queue:RequestQueue ?= null

    fun refresh(){
        homesLoadErrorLD.value = false
        loadingLD.value = true

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/hobbyApp/news.json"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object :TypeToken<List<News>>() {}.type
                val result = Gson().fromJson<List<News>>(it, sType)
                homesLD.value = result as ArrayList<News>?
                loadingLD.value = false

                Log.d("showVolley", result.toString())
            },
            {
                Log.d("showVolley", it.toString())
                loadingLD.value = false
                homesLoadErrorLD.value = false
            }
        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}