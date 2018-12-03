package com.ayvytr.mvptest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.ayvytr.mvprxlifecycle.RxUtils
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class LifecycleTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle_test)
        initView()
    }

    private fun initView() {}

    fun onLongTimeTask(view: View) {
        Observable.interval(1, TimeUnit.SECONDS)
            .bindToLifecycle(this)
            .compose(RxUtils.ofDefault(null))
            .subscribe {
                Log.e("tag", it.toString())
            }
    }
}
