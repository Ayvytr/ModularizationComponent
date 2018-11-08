package com.ayvytr.mvprxlifecycle.mvp.presenter;

import com.ayvytr.logger.L;
import com.ayvytr.mvpbase.BasePresenter;
import com.ayvytr.mvprxlifecycle.RxUtils;
import com.ayvytr.mvprxlifecycle.mvp.contract.MainContract;
import com.ayvytr.mvprxlifecycle.mvp.model.MainModel;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author admin
 */
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    public MainPresenter(MainContract.View rootView) {
        super(new MainModel(), rootView);
    }

    public void getData() {
        mModel.getData()
              .compose(RxUtils.<Long>bindToLifecycle(mView))
              .compose(RxUtils.<Long>subscribeIo(mView))
              .subscribe(new Observer<Long>() {
                  @Override
                  public void onSubscribe(Disposable d) {
                      L.e(Thread.currentThread().getName());
                  }

                  @Override
                  public void onNext(Long aLong) {
                      L.e(Thread.currentThread().getName(), aLong);
                      mView.showInterval(aLong);
                  }

                  @Override
                  public void onError(Throwable e) {
                      L.e(Thread.currentThread().getName());
                  }

                  @Override
                  public void onComplete() {
                      L.e(Thread.currentThread().getName());
                  }
              });
    }

    public void getWeather() {
        mModel.getWeather()
              .compose(RxUtils.<String>bindToLifecycle(mView))
              .compose(RxUtils.<String>subscribeIo(mView))
              .subscribe(new Consumer<String>() {
                  @Override
                  public void accept(String weather) {
                      mView.showWeather(weather);
                  }
              });
    }
}
