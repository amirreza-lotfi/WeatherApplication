package com.amirreza.presentation.weatherapplication.CityFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amirreza.domain.entity.CityDailyWeather
import com.amirreza.domain.entity.OneCallWeatherEntitys.CityHourlyWeather
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
import java.util.*

class CityFragmentViewModel(
    private val apiService: WeatherService,
    private val daoDatabase: WatchListRepository
):WeatherViewModel() {
    private val _hasAnyCityInDatabase = MutableLiveData(false)
    val hasAnyCityInDatabase:LiveData<Boolean> = _hasAnyCityInDatabase

    private val _isInternetConnectionStable = MutableLiveData(true)
    val isInternetConnectionStable:LiveData<Boolean> = _isInternetConnectionStable

    private val _theDataHasNotGetFromServerYet = MutableLiveData(true)
    val theDataHasNotGetFromServerYet:LiveData<Boolean> = _theDataHasNotGetFromServerYet

    private var _citiesInWatchList: MutableList<WatchListWeather>? = null
    var citiesInWatchList = _citiesInWatchList

    private var firstCityInWatchList:WatchListWeather? = null
    private var _weatherOfTopCity:CityWeatherAllInformation? =null

    val weatherOfTopCity: CityWeatherAllInformation? = _weatherOfTopCity

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
                    _theDataHasNotGetFromServerYet.value = true

                    if(list.isNotEmpty()){
                        _hasAnyCityInDatabase.value = true
                        _citiesInWatchList = list.toMutableList()
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
                    _weatherOfTopCity = cityWeatherAllInformation
                    _theDataHasNotGetFromServerYet.value = false
                    addCityToWatchList()
                }

                override fun onError(e: Throwable) {
                    _isInternetConnectionStable.value = false
                }
            })
    }

    fun addCityToWatchList(){
        if(weatherOfTopCity!=null && _citiesInWatchList!=null){
            val watchListWeather = WatchListWeather(
                getCityName(),
                getCountryName(),
                weatherOfTopCity.current.timeDescription,
                weatherOfTopCity.current.temperature.toString(),
                weatherOfTopCity.getWeatherImagePath().toString(),
                weatherOfTopCity.lat,
                weatherOfTopCity.lon,
                Calendar.getInstance().time.time
            )
            for (i in _citiesInWatchList!!.indices) {
                if (watchListWeather.cityName == _citiesInWatchList!![i].cityName) {
                    _citiesInWatchList!![i] = watchListWeather
                }
            }
        }
    }
    fun getAllCitiesInWatchList():ArrayList<WatchListWeather>{
        if(citiesInWatchList!=null) {
            val watchList = arrayListOf<WatchListWeather>()
            watchList.addAll(citiesInWatchList!!)
            return watchList
        }
        return arrayListOf()
    }
    fun getHourlyWeather():List<CityHourlyWeather>{
        val DAY_TO_MILLI_SECOND = 86452
        val MAXIMUM_NUMBER_WEATHER = 23

        val cityHourlyWeather = weatherOfTopCity?.current?.cityHourlyWeather ?: listOf()

        var i = 0
        while (i < cityHourlyWeather.size && i < MAXIMUM_NUMBER_WEATHER) {
            val weatherHour: CityHourlyWeather = cityHourlyWeather[i]
            val sunrise: Long = (weatherOfTopCity?.current?.sunrise ?: 0L) as Long
            val sunset: Long = (weatherOfTopCity?.current?.sunset ?: 0L) as Long
            val dt: Int = weatherHour.requestTime
            if (dt > sunrise && dt > sunset) {
                cityHourlyWeather[i].setImage(sunrise + DAY_TO_MILLI_SECOND, sunset + DAY_TO_MILLI_SECOND)
            } else {
                cityHourlyWeather[i].setImage(sunrise, sunset)
            }
            ++i
        }
        return weatherOfTopCity?.current?.cityHourlyWeather ?: listOf()
    }
    fun getDailyWeather():List<CityDailyWeather>{
        return weatherOfTopCity?.current?.daily ?: listOf()
    }
    fun hasNotAnyCityInDatabase(): Boolean {
        return !hasAnyCityInDatabase.value!!
    }
    fun getCityNameWithCountry():String{
        return "${firstCityInWatchList?.cityName}, ${firstCityInWatchList?.country}"
    }
    fun getCityName():String{
        return firstCityInWatchList?.cityName ?: ""
    }
    fun getCountryName():String{
        return firstCityInWatchList?.country ?: ""
    }
    fun onRefreshData(){
        setHasAnyCityInDatabase()
    }
}