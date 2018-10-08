package com.ayvytr.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * @author admin
 */
public abstract class BaseMvpActivity<P extends IPresenter> extends RxAppCompatActivity implements IView, IInit {
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int contentViewRes = getContentViewRes();
        if(contentViewRes > 0) {
            setContentView(contentViewRes);
        }
        ButterKnife.bind(this);
        mPresenter = getPresenter();
        initExtra();
        initView(savedInstanceState);
        initData(savedInstanceState);
    }

    protected abstract P getPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }

    public Context getContext() {
        return this;
    }

    public AppCompatActivity getActivity() {
        return this;
    }

    public void toast(String msg) {
        Toast.makeText(BaseMvpActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    public void toast(@StringRes int id) {
        Toast.makeText(BaseMvpActivity.this, id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
