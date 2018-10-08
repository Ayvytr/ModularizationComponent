package com.ayvytr.mvp.mvp.contract;

import com.ayvytr.mvp.IModel;
import com.ayvytr.mvp.IView;

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
