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

class DetailViewModel(application: Application):AndroidViewModel(application) {
    val homesDetailLD = MutableLiveData<News>()
    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun fetchDetail(id:String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://10.0.2.2/hobbyApp/detailNews.php?id=${id}"

        val stringRequest = StringRequest(
            Request.Method.GET, url, {
                homesDetailLD.value = Gson().fromJson(it, News::class.java)
                Log.d("showVolley", it)
            },
            {
                Log.d("ShowVolley", it.toString())
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