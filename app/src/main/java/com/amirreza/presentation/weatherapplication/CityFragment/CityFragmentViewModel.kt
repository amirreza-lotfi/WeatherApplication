package com.amirreza.presentation.weatherapplication.CityFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amirreza.domain.entity.OneCallWeatherEntitys.CityWeatherAllInformation
import com.amirreza.domain.entity.WatchListWeather
import com.amirreza.domain.repository.WatchListRepository
import com.amirreza.domain.repository.WeatherService
import com.amirreza.presentation.weatherapplication.MainActivity
import com.amirreza.presentation.weatherapplication.base.WeatherViewModel
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CityFragmentViewModel(
    private val apiService: WeatherService,
    private val daoDatabase: WatchListRepository
):WeatherViewModel() {
    private val _hasAnyCityInDatabase = MutableLiveData(false)
    val hasAnyCityInDatabase:LiveData<Boolean> = _hasAnyCityInDatabase

    private val _isInternetConnectionStable = MutableLiveData(true)
    val isInternetConnectionStable:LiveData<Boolean> = _isInternetConnectionStable

    private var firstCityInWatchList:WatchListWeather? = null
    private var weatherOfTopCity:CityWeatherAllInformation? =null

    init {
        setHasAnyCityInDatabase()
    }

    private fun setHasAnyCityInDatabase(){
        daoDatabase.getAllCities()
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<WatchListWeather>>{
                override fun onSubscribe(d: Disposable) {
                    TODO("Not yet implemented")
                }

                override fun onSuccess(list: List<WatchListWeather>) {
                    if(list.isNotEmpty()){
                        _hasAnyCityInDatabase.value = true
                        firstCityInWatchList = list[0]
                        getTopCityWeatherFromServer(firstCityInWatchList!!)
                    }else{
                        _hasAnyCityInDatabase.value = false
                    }
                }

                override fun onError(e: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }
    private fun getTopCityWeatherFromServer(watchListWeather: WatchListWeather){
        apiService.getCityWeather(
            watchListWeather.lat,
            watchListWeather.lon,
            "metric",
            MainActivity.API_KEY
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<CityWeatherAllInformation>{
                override fun onSubscribe(d: Disposable) {
                    TODO("Not yet implemented")
                }

                override fun onSuccess(cityWeatherAllInformation: CityWeatherAllInformation) {
                    weatherOfTopCity = cityWeatherAllInformation
                }

                override fun onError(e: Throwable) {
                    _isInternetConnectionStable.value = false
                }
            })
    }
    fun hasNotAnyCityInDatabase(): Boolean {
        return !hasAnyCityInDatabase.value!!
    }

}