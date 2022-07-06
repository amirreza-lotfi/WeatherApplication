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
import com.amirreza.presentation.weatherapplication.base.CitySingleObserver
import com.amirreza.presentation.weatherapplication.base.WeatherViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class CityFragmentViewModel(
    private val apiService: WeatherService,
    private val daoDatabase: WatchListRepository
):WeatherViewModel() {

    private var cityName:String = ""
    var countryName:String = ""
        private set

    private val _mustIntroLottieAnimationShow = MutableLiveData(true)
    val mustIntroLottieAnimationShow:LiveData<Boolean> =_mustIntroLottieAnimationShow

    private val _mustNavigateToSearchFragment = MutableLiveData(false)
    val mustNavigateToSearchFragment:LiveData<Boolean> =_mustNavigateToSearchFragment


    val transactionToDialogFragment = MutableLiveData(false)

    private var wantToDeleteCity:WatchListWeather? = null


    private val _hasProblemInGettingDataFromServer = MutableLiveData(true)
    val hasProblemInGettingDataFromServer:LiveData<Boolean> = _hasProblemInGettingDataFromServer


    private var _citiesInWatchList: MutableList<WatchListWeather> = mutableListOf()
    var citiesInWatchList:MutableList<WatchListWeather> = _citiesInWatchList

    private var _weatherOfTopCity:CityWeatherAllInformation? =null
    val weatherOfTopCity: CityWeatherAllInformation? = _weatherOfTopCity


    fun getData(){
        fetchWatchListCitiesFromDataBase()
    }

    private fun fetchWatchListCitiesFromDataBase(){
        daoDatabase.getAllCities()
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CitySingleObserver<List<WatchListWeather>>(compositeDisposable) {
                override fun onSuccess(list: List<WatchListWeather>) {
                    if(list.isEmpty()){
                        appShouldNavigateToSearchFragment()
                    }else{
                        _citiesInWatchList = list.toMutableList()
                        setCityNameAndCountryName(_citiesInWatchList[0])
                        getTopCityWeatherFromServer(_citiesInWatchList[0])
                    }
                }
            })
    }

    private fun appShouldNavigateToSearchFragment(){
        _mustNavigateToSearchFragment.value = true
    }

    private fun setCityNameAndCountryName(watchListWeather: WatchListWeather){
        cityName = watchListWeather.cityName
        countryName = watchListWeather.country
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
            .subscribe(object : CitySingleObserver<CityWeatherAllInformation>(compositeDisposable){
                override fun onSuccess(cityWeatherAllInformation: CityWeatherAllInformation) {
                    _weatherOfTopCity = cityWeatherAllInformation
                    addCityToWatchList()
                }
            })
    }

    private fun addCityToWatchList(){
        val newWatchListWeather = WatchListWeather.convertCityWeatherAllDataToWatchListWeather(cityName,countryName,_weatherOfTopCity!!)
        for (i in _citiesInWatchList.indices) {
            if (newWatchListWeather.cityName == _citiesInWatchList[i].cityName) {
                _citiesInWatchList[i] = newWatchListWeather
            }
        }
        daoDatabase.addCityToWatchList(newWatchListWeather)
    }


    fun uiEvent(event: CityFragmentEvent){
        when (event){
            is CityFragmentEvent.LandingViewAnimationEnd->{
                _mustIntroLottieAnimationShow.value = false
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

    fun getCityNameWithCountry():String = "$cityName, $countryName"

    fun onRefreshData(){
        getData()
    }

    fun deleteWatchList(){
        if(wantToDeleteCity!=null) {
            citiesInWatchList.remove(wantToDeleteCity)
            daoDatabase.deleteCityFromWatchList(wantToDeleteCity!!)
        }
    }
    fun addCityToDatabaseFromSearchFragment(watchListWeather: WatchListWeather){
        setCityNameAndCountryName(watchListWeather)
        daoDatabase.addCityToWatchList(watchListWeather)
    }
}