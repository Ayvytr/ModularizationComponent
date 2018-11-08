package com.ayvytr.mvprxlifecycle.mvp.contract;


import com.ayvytr.mvpbase.IModel;
import com.ayvytr.mvpbase.IView;

import io.reactivex.Observable;

/**
 * @author admin
 */
public class MainContract {
    public interface View extends IView {
        void showInterval(Long aLong);

        void showWeather(String weather);
    }

    public interface Model extends IModel {
        Observable<Long> getData();

        Observable<String> getWeather();
    }
}
