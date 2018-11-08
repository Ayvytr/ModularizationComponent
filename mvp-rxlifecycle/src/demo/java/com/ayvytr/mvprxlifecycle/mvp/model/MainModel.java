package com.ayvytr.mvprxlifecycle.mvp.model;

import com.ayvytr.mvprxlifecycle.mvp.contract.MainContract;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * @author admin
 */
public class MainModel implements MainContract.Model {

    public MainModel() {
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public Observable<Long> getData() {
        return Observable.interval(0, 1, TimeUnit.SECONDS);
    }

    @Override
    public Observable<String> getWeather() {
        return Observable.timer(3, TimeUnit.SECONDS)
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(Long aLong) {
                        return "深圳天气：中雨";
                    }
                });
    }
}
