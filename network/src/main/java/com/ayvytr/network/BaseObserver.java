package com.ayvytr.network;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author admin
 */
public abstract class BaseObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onComplete() {
    }
}
