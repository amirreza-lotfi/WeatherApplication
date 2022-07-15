package com.amirreza.presentation.weatherapplication.LandingFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amirreza.common.base.CitySingleObserver
import com.amirreza.common.base.WeatherViewModel
import com.amirreza.common.base.asyncRequest
import com.amirreza.domain.usecases.WatchListUsecase

class LandingViewModel(private val usecase: WatchListUsecase):WeatherViewModel() {
    private val _countOfSavedCity = MutableLiveData<Int>()
    val countOfSavedCity: LiveData<Int> = _countOfSavedCity

    init {
        getWatchListCount()
    }

    private fun getWatchListCount(){
        usecase.getCountOfSavedCity()
            .asyncRequest()
            .subscribe(object : CitySingleObserver<Int>(compositeDisposable){
                override fun onSuccess(t: Int) {
                    _countOfSavedCity.value = t
                }

                override fun onError(e: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }
}