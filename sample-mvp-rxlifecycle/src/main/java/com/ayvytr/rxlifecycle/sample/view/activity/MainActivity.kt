package com.ayvytr.rxlifecycle.sample.view.activity

import com.ayvytr.rxlifecycle.BaseMvpActivity
import com.ayvytr.rxlifecycle.sample.presenter.MainPresenter
import com.ayvytr.rxlifecycle.sample.contract.MainContract
import com.ayvytr.rxlifecycle.sample.model.MainModel
import android.os.Bundle

class MainActivity : BaseMvpActivity<MainPresenter>(), MainContract.View {

    override fun getPresenter(): MainPresenter {
        return MainPresenter(MainModel(), this)
    }

    override fun initExtra() {

    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getContentViewRes(): Int {
        return 0;
    }
}
