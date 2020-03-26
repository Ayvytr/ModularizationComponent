package com.ayvytr.app

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.ayvytr.ktx.context.toast
import com.ayvytr.ktx.ui.hide
import com.ayvytr.ktx.ui.show
import com.ayvytr.logger.L
import com.ayvytr.network.ApiClient
import com.ayvytr.network.isNetworkAvailable
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var gank: Gank

    private lateinit var tv: TextView
    private lateinit var btnGetData: Button
    private lateinit var pb: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ApiClient.getInstance().init("https://gank.io/api/", 30)
        gank = ApiClient.getInstance().create(Gank::class.java)

        tv = findViewById<TextView>(R.id.tv)
        btnGetData = findViewById(R.id.btn_get_data)
        pb = findViewById(R.id.pb)

        btnGetData.setOnClickListener {
            tv.text = null
            requestData()
        }

        L.e(cacheDir.absolutePath)
        requestData()

        Environment.getExternalStorageState()
    }

    val handler = CoroutineExceptionHandler { _, exception ->
//        println("Caught $exception")
        pb.hide()
        toast(exception.toString())
    }
    private fun requestData() {
        pb.show()
        L.e(isNetworkAvailable())

        val uiScope = CoroutineScope(Dispatchers.Main)
        uiScope.launch(handler) {
//            try {
//                val data = gank.getData()
                val data= async{gank.getData()}.await()
                val android= async{gank.getAndroid()}.await()

                tv.text = data
                tv.append("\n\n")
                tv.append(android)
            pb.hide()
//            } catch (e: Exception) {
//                pb.hide()
//                tv.text = e.toString()
//            }
        }

//            .enqueue(object : Callback<String> {
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                pb.hide()
//                tv.text = t.toString()
//            }
//
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                pb.hide()
//                tv.text = response.body()
//            }

//        })
    }
}
