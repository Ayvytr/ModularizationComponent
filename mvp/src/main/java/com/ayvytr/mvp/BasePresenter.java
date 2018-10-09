package com.ayvytr.mvp;

import io.reactivex.annotations.NonNull;

/**
 * @author ayvytr
 */
public class BasePresenter<M extends IModel, V extends IView> implements IPresenter{
    protected M mModel;
    protected V mView;

    public BasePresenter() {
        onCreate();
    }

    /**
     * 如果当前页面同时需要 Model 层和 View 层,则使用此构造函数(默认)
     *
     * @param model
     * @param rootView
     */
    public BasePresenter(@NonNull M model, @NonNull V rootView) {
        this.mModel = model;
        this.mView = rootView;
        onCreate();
    }

    /**
     * 如果当前页面不需要操作数据,只需要 View 层,则使用此构造函数
     *
     * @param rootView
     */
    public BasePresenter(@NonNull V rootView) {
        this.mView = rootView;
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

}
