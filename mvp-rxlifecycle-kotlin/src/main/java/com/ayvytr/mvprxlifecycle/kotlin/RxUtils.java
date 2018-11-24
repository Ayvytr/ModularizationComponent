/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ayvytr.mvprxlifecycle.kotlin;

import android.support.annotation.Nullable;

import com.ayvytr.mvpbase.IView;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ayvytr
 * ================================================
 * 使用此类操作 RxLifecycle 的特性
 * <p>
 * Created by JessYan on 26/08/2017 17:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */

public class RxUtils {

    private RxUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    /**
     * 封装线程切换和loading显示
     *
     * @param view IView
     * @param <T>  返回数据
     * @return ObservableTransformer
     */
    @NonNull
    public static <T> ObservableTransformer<T, T> subscribeIo(@Nullable final IView view) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                               .doOnSubscribe(new Consumer<Disposable>() {
                                   @Override
                                   public void accept(Disposable disposable) {
                                       // 显示loading
                                       if(view != null) {
                                           view.showLoading();
                                       }
                                   }
                               })
                               //设置doOnSubscribe对应的线程为主线程
                               .subscribeOn(AndroidSchedulers.mainThread())
                               .observeOn(AndroidSchedulers.mainThread())
                               .doFinally(new Action() {
                                   @Override
                                   public void run() {
                                       // 隐藏loading
                                       if(view != null) {
                                           view.hideLoading();
                                       }
                                   }
                               });
            }
        };
    }

    /**
     * 全程都在指定线程执行的变换操作
     *
     * @param scheduler {@link Scheduler}
     */
    public static <T> ObservableTransformer<T, T> ofScheduler(final Scheduler scheduler) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(scheduler)
                               .observeOn(scheduler);
            }
        };
    }
}
