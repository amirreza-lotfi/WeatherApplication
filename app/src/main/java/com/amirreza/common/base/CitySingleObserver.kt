package com.amirreza.common.base

import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class CitySingleObserver<T>(private val compositeDisposable: CompositeDisposable): SingleObserver<T> {
    override fun onSubscribe(d: Disposable) {
        compositeDisposable.add(d)
    }
}