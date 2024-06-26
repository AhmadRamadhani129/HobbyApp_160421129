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
import com.example.hobbyapp_160421129.util.buildDb
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailViewModel(application: Application):AndroidViewModel(application), CoroutineScope {
    val homesDetailLD = MutableLiveData<News>()

    private val job = Job()
//    val TAG = "volleyTag"
//    private var queue: RequestQueue? = null

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun fetchDetail(id:Int){
        launch {
            val db = buildDb(getApplication())
            homesDetailLD.postValue(db.hobbyDao().selectNews(id))
        }
//        queue = Volley.newRequestQueue(getApplication())
//        val url = "http://10.0.2.2/hobbyApp/detailNews.php?id=${id}"
//
//        val stringRequest = StringRequest(
//            Request.Method.GET, url, {
//                homesDetailLD.value = Gson().fromJson(it, News::class.java)
//                Log.d("showVolley", it)
//            },
//            {
//                Log.d("ShowVolley", it.toString())
//            }
//        )
//        stringRequest.tag = TAG
//        queue?.add(stringRequest)
    }

//    override fun onCleared() {
//        super.onCleared()
//        queue?.cancelAll(TAG)
//    }


}