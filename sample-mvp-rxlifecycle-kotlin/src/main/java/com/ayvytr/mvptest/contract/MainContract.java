package com.ayvytr.mvptest.contract;

import com.ayvytr.mvpbase.IModel;
import com.ayvytr.mvpbase.IView;

public class MainContract {
    public interface Model extends IModel {}

    public interface View extends IView {
        void showTimer(Long it);
    }
}
