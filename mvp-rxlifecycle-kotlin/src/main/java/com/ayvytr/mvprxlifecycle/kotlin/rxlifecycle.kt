package com.ayvytr.mvprxlifecycle.kotlin

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import com.ayvytr.mvp.IView
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindToLifecycle
import com.trello.rxlifecycle2.android.lifecycle.kotlin.bindUntilEvent
import io.reactivex.*

/**
 * @author ayvytr
 */

/**
 * @param view [IView]
 */
fun <T> Observable<T>.bindToLifecycle(view: IView): Observable<T>
        = bindToLifecycle(view as LifecycleOwner)

fun <T> Observable<T>.bindUntilEvent(view: IView, event: Lifecycle.Event): Observable<T>
        = bindUntilEvent(view as LifecycleOwner, event)

fun <T> Flowable<T>.bindToLifecycle(view: IView): Flowable<T>
        = bindToLifecycle(view as LifecycleOwner)

fun <T> Flowable<T>.bindUntilEvent(view: IView, event: Lifecycle.Event): Flowable<T>
        = bindUntilEvent(view as LifecycleOwner, event)

fun <T> Single<T>.bindToLifecycle(view: IView): Single<T>
        = bindToLifecycle(view as LifecycleOwner)

fun <T> Single<T>.bindUntilEvent(view: IView, event: Lifecycle.Event): Single<T>
        = bindUntilEvent(view as LifecycleOwner, event)

fun <T> Maybe<T>.bindToLifecycle(view: IView): Maybe<T>
        = bindToLifecycle(view as LifecycleOwner)

fun <T> Maybe<T>.bindUntilEvent(view: IView, event: Lifecycle.Event): Maybe<T>
        = bindUntilEvent(view as LifecycleOwner, event)

fun Completable.bindToLifecycle(view: IView): Completable
        = bindToLifecycle(view as LifecycleOwner)

fun Completable.bindUntilEvent(view: IView, event: Lifecycle.Event): Completable
        = bindUntilEvent(view as LifecycleOwner, event)
