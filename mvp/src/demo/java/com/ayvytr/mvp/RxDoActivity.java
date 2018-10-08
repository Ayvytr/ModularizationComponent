package com.ayvytr.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ayvytr.logger.L;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class RxDoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Observable.just(1)
                  .doOnSubscribe(new Consumer<Disposable>() {
                      @Override
                      public void accept(Disposable disposable) throws Exception {
                          L.e();
                      }
                  })
                  .doOnDispose(new Action() {
                      @Override
                      public void run() throws Exception {
                          L.e();
                      }
                  })
                  .doOnComplete(new Action() {
                      @Override
                      public void run() throws Exception {
                          L.e();
                      }
                  })
                  .doAfterNext(new Consumer<Integer>() {
                      @Override
                      public void accept(Integer integer) throws Exception {
                          L.e();
                      }
                  })
                  .doOnEach(new Consumer<Notification<Integer>>() {
                      @Override
                      public void accept(Notification<Integer> integerNotification) throws Exception {

                          L.e();
                      }
                  })
                  .doOnError(new Consumer<Throwable>() {
                      @Override
                      public void accept(Throwable throwable) throws Exception {
                          L.e();
                      }
                  })
                  .doFinally(new Action() {
                      @Override
                      public void run() throws Exception {
                          L.e();
                      }
                  })
                  .subscribe(new Observer<Integer>() {
                      @Override
                      public void onSubscribe(Disposable d) {
                          L.e();
                      }

                      @Override
                      public void onNext(Integer integer) {
                          L.e();
                      }

                      @Override
                      public void onError(Throwable e) {
                          L.e();
                      }

                      @Override
                      public void onComplete() {
                          L.e();
                      }
                  });
    }
}
