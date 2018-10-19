package com.ayvytr.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.ayvytr.customview.loading.StatusView;
import com.ayvytr.easykotlin.context.ToastKt;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * @author admin
 */
public abstract class BaseMvpActivity<P extends IPresenter> extends RxAppCompatActivity implements IView, IInit {
    protected P mPresenter;

    /**
     * 一定要判空
     */
    @Nullable
    protected StatusView mStatusView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int contentViewRes = getContentViewRes();
        if(contentViewRes > 0) {
            if(useStatusView()) {
                mStatusView = new StatusView(getContext());
                mStatusView.setContentView(LayoutInflater.from(getContext()).inflate(contentViewRes, null));
                setContentView(mStatusView);
            } else {
                setContentView(contentViewRes);
            }
        }
        ButterKnife.bind(this);
        mPresenter = getPresenter();
        initExtra();
        initView(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public boolean useStatusView() {
        return false;
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


    @Override
    public void showMessage(String message) {
        ToastKt.toast(getContext(), message);
    }

    @Override
    public void showMessage(int stringId) {
        ToastKt.toast(getContext(), stringId);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {
        ToastKt.toast(getContext(), errorMsg);
    }

    @Override
    public void showError(int stringId) {
        ToastKt.toast(getContext(), stringId);
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
