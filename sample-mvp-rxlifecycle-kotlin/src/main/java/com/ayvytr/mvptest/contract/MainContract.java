package com.ayvytr.mvptest.contract;

import com.ayvytr.mvp.IModel;
import com.ayvytr.mvp.IView;

public class MainContract {
    public interface Model extends IModel {}

    public interface View extends IView {
        void showTimer(Long it);
    }
}
