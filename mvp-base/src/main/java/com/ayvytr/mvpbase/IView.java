package com.ayvytr.mvpbase;

import android.support.annotation.StringRes;

/**
 * @author ayvytr
 */
public interface IView {

    void showMessage(String message);

    void showMessage(@StringRes int stringId);

    void showLoading();

    void hideLoading();

    void showError(String errorMsg);

    void showError(@StringRes int stringId);

    void showEmpty();
}
