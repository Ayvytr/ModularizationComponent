package com.ayvytr.mvpbase;

import android.support.annotation.StringRes;

/**
 * @author ayvytr
 */
public interface IView {

    /**
     * 显示消息
     *
     * @param message String message
     */
    void showMessage(String message);

    /**
     * 显示消息
     *
     * @param stringId String id
     */
    void showMessage(@StringRes int stringId);

    /**
     * 显示加载
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示错误
     *
     * @param errorMsg Error message
     */
    void showError(String errorMsg);

    /**
     * 显示错误
     *
     * @param stringId Error string id
     */
    void showError(@StringRes int stringId);

    /**
     * 显示空数据ui
     */
    void showEmpty();
}
