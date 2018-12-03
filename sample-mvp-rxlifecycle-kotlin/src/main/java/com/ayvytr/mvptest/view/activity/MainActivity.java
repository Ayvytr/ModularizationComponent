package com.ayvytr.mvptest.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ayvytr.mvprxlifecycle.BaseMvpActivity;
import com.ayvytr.mvptest.R;
import com.ayvytr.mvptest.contract.MainContract;
import com.ayvytr.mvptest.model.MainModel;
import com.ayvytr.mvptest.presenter.MainPresenter;

public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.View {

    private TextView tv;

    @Override
    protected MainPresenter getPresenter() {
        return new MainPresenter(new MainModel(), this);
    }

    @Override
    public void initExtra() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tv = findViewById(R.id.tv);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.timer();
    }

    @Override
    public int getContentViewRes() {
        return R.layout.activity_main;
    }

    @Override
    public void showTimer(Long it) {
        tv.setText(String.valueOf(it));
        Log.e(getClass().getSimpleName(), String.valueOf(it));
    }

    public void onDialog(View view) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("标题")
                .setMessage("内容")
                .show();
    }
}
