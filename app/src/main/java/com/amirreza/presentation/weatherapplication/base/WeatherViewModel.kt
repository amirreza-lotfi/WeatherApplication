package com.amirreza.presentation.weatherapplication.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class WeatherViewModel: ViewModel() {
    protected val compositeDisposable = CompositeDisposable()
    val progressBarLiveData = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}