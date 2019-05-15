package com.ayvytr.rxlifecycle;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ayvytr.mvp.IInit;
import com.ayvytr.mvp.IPresenter;
import com.ayvytr.mvp.IView;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * @author ayvytr
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
        mPresenter = getPresenter();
        initExtra();
        initView(savedInstanceState);
        initData(savedInstanceState);
    }

    @Nullable
    protected P getPresenter() {
        return null;
    }

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


    @Override
    public void showMessage(String message) {
        Toast.makeText(BaseMvpActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int stringId) {
        Toast.makeText(BaseMvpActivity.this, stringId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {
        Toast.makeText(BaseMvpActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(int stringId) {
        Toast.makeText(BaseMvpActivity.this, stringId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void initExtra() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public int getContentViewRes() {
        return 0;
    }
}
