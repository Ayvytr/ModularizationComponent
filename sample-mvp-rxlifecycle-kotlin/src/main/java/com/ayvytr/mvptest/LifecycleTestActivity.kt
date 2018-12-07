package com.ayvytr.mvptest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.ayvytr.network.NetworkUtils
import com.ayvytr.rxlifecycle.RxUtils
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.Observable
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit

class LifecycleTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle_test)
        initView()
    }

    private fun initView() {
        toast(NetworkUtils.getNetworkType(this).toString())
    }

    fun onLongTimeTask(view: View) {
        Observable.interval(1, TimeUnit.SECONDS)
            .bindToLifecycle(this)
            .compose(RxUtils.ofDefault(null))
            .subscribe {
                Log.e("tag", it.toString())
            }
    }
}
