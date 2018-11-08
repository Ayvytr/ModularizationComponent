package com.ayvytr.mvprxlifecycle.mvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.ayvytr.mvp.R;
import com.ayvytr.mvprxlifecycle.BaseMvpActivity;
import com.ayvytr.mvprxlifecycle.mvp.contract.MainContract;
import com.ayvytr.mvprxlifecycle.mvp.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tvWeather)
    TextView tvWeather;

    @Override
    protected MainPresenter getPresenter() {
        return new MainPresenter(this);
    }


    @Override
    public void initExtra() {
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mPresenter.getData();
        mPresenter.getWeather();
    }

    @Override
    public int getContentViewRes() {
        return R.layout.activity_main;
    }

    @Override
    public void showInterval(Long aLong) {
        tv.setText(String.valueOf(aLong));
    }

    @Override
    public void showWeather(String weather) {
        tvWeather.setText(weather);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
