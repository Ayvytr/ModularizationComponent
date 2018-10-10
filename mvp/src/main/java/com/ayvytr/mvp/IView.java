package com.ayvytr.mvp;

import android.support.annotation.StringRes;

/**
 * @author admin
 */
public interface IView {

    void showMessage(String message);

    void showMessage(@StringRes int stringId);

    void showLoading();

    void hideLoading();
}
