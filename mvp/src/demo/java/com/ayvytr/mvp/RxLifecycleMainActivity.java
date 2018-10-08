package com.ayvytr.mvp;

import android.os.Bundle;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class RxLifecycleMainActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 当执行onDestory()时， 自动解除订阅
        Observable.interval(1, TimeUnit.SECONDS)
                  .doOnDispose(new Action() {
                      @Override
                      public void run() {
                      }
                  })
                  .compose(this.<Long>bindToLifecycle())
                  .subscribe(new Consumer<Long>() {
                      @Override
                      public void accept(Long num) {
                      }
                  });

        Observable.interval(1, TimeUnit.SECONDS)
                  .doOnDispose(new Action() {
                      @Override
                      public void run() {
                      }
                  })
                  //bindUntilEvent()，内部传入指定生命周期参数
                  .compose(this.<Long>bindUntilEvent(ActivityEvent.DESTROY))
                  .subscribe(new Consumer<Long>() {
                      @Override
                      public void accept(Long num) {
                      }
                  });
    }
}

