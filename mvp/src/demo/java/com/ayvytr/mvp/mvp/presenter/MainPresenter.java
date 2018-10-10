package com.ayvytr.mvp.mvp.presenter;

import com.ayvytr.logger.L;
import com.ayvytr.mvp.BasePresenter;
import com.ayvytr.mvp.RxUtils;
import com.ayvytr.mvp.mvp.contract.MainContract;
import com.ayvytr.mvp.mvp.model.MainModel;

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
              .compose(RxUtils.<Long>applySchedulers(mView))
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
              .compose(RxUtils.<String>applySchedulers(mView))
              .subscribe(new Consumer<String>() {
                  @Override
                  public void accept(String weather) {
                      mView.showWeather(weather);
                  }
              });
    }
}
