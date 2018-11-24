package com.ayvytr.mvptest.presenter

import com.ayvytr.mvpbase.BasePresenter
import com.ayvytr.mvprxlifecycle.kotlin.RxUtils
import com.ayvytr.mvprxlifecycle.kotlin.bindToLifecycle
import com.ayvytr.mvptest.contract.MainContract
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class MainPresenter : BasePresenter<MainContract.Model, MainContract.View> {

    constructor() {}

    constructor(model: MainContract.Model, view: MainContract.View) : super(model, view) {}

    constructor(view: MainContract.View) : super(view) {
    }

    fun timer() {
        Observable.interval(1, TimeUnit.SECONDS)
            .bindToLifecycle(mView)
            .compose(RxUtils.subscribeIo(null))
            .subscribe{
                mView.showTimer(it)
            }
    }
}


