package com.ayvytr.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author admin
 */
public abstract class BaseMvpFragment<P extends IPresenter> extends RxFragment
        implements IView, IInit{
    protected View mContentView;
    protected boolean isViewCreated;

    protected P mPresenter;
    protected Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        int contentViewRes = getContentViewRes();
        if(contentViewRes > 0) {
            mContentView = inflater.inflate(contentViewRes, container, false);
        }

        return mContentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        mPresenter = getPresenter();
        mUnbinder = ButterKnife.bind(this, view);
        initExtra();
        initView(savedInstanceState);
        initData(savedInstanceState);
    }

    protected abstract P getPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        if(mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(int stringId) {
        Toast.makeText(getContext(), stringId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }
}
