package com.ayvytr.rxlifecycle.sample.presenter

import com.ayvytr.mvp.BasePresenter
import com.ayvytr.rxlifecycle.sample.contract.MainContract

class MainPresenter : BasePresenter<MainContract.Model, MainContract.View> {
    constructor() {
    }

    constructor(model: MainContract.Model, view: MainContract.View) : super(model, view) {
        ;
    }

    constructor(view: MainContract.View) : super(view) {
    }
}
