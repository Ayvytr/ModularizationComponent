package com.ayvytr.app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.ayvytr.network.kotlin.ApiClient
import com.ayvytr.network.kotlin.async
import kotlinx.android.synthetic.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var gank: Gank

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ApiClient.getInstance().init("https://gank.io/api/", this, true)
        gank = ApiClient.getInstance().create(Gank::class.java)

        val tv = findViewById<TextView>(R.id.tv)

        gank.getData().enqueue(object:Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                tv.text = t.toString()
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                tv.text = response.body()
            }

        })
    }
}
