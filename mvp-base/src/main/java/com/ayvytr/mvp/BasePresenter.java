package com.ayvytr.mvp;

import android.support.annotation.NonNull;

/**
 * @author ayvytr
 */
public class BasePresenter<M extends IModel, V extends IView> implements IPresenter {
    protected M mModel;
    protected V mView;

    public BasePresenter() {
        onCreate();
    }

    /**
     * 如果当前页面同时需要 Model 层和 View 层,则使用此构造函数(默认)
     *
     * @param model
     * @param view
     */
    public BasePresenter(@NonNull M model, @NonNull V view) {
        this.mModel = model;
        this.mView = view;
        onCreate();
    }

    /**
     * 如果当前页面不需要操作数据,只需要 View 层,则使用此构造函数
     *
     * @param view
     */
    public BasePresenter(@NonNull V view) {
        this.mView = view;
        onCreate();
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
        if(mModel != null) {
            mModel.onDestroy();
            this.mModel = null;
        }
    }

    public void handlerErrorMessage(String errorMsg) {
        if(mView != null) {
            mView.showError(errorMsg);
        }
    }
}
