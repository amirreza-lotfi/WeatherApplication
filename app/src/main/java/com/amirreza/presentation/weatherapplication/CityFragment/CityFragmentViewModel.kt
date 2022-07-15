package com.amirreza.presentation.weatherapplication.CityFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amirreza.domain.entity.CityAllWeatherDataEntity.CityAllWeatherData
import com.amirreza.domain.entity.CityAllWeatherDataEntity.DailyWeather
import com.amirreza.domain.entity.CityAllWeatherDataEntity.HourlyWeather
import com.amirreza.domain.entity.SavedCityWeather
import com.amirreza.domain.repository.WatchListRepository
import com.amirreza.domain.repository.WeatherService
import com.amirreza.presentation.weatherapplication.MainActivity
import com.amirreza.presentation.weatherapplication.base.CitySingleObserver
import com.amirreza.presentation.weatherapplication.base.WeatherViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CityFragmentViewModel(
    private val apiService: WeatherService,
    private val daoDatabase: WatchListRepository
) : WeatherViewModel() {

    private var cityName: String = ""
    var countryName: String = ""
        private set

    private val _mustIntroLottieAnimationShow = MutableLiveData(true)
    val mustIntroLottieAnimationShow: LiveData<Boolean> = _mustIntroLottieAnimationShow

    private val _mustNavigateToSearchFragment = MutableLiveData(false)
    val mustNavigateToSearchFragment: LiveData<Boolean> = _mustNavigateToSearchFragment


    val transactionToDialogFragment = MutableLiveData(false)

    private var wantToDeleteCity: SavedCityWeather? = null


    private val _hasProblemInGettingDataFromServer = MutableLiveData(true)
    val hasProblemInGettingDataFromServer: LiveData<Boolean> = _hasProblemInGettingDataFromServer


    private var _citiesInWatchList: MutableList<SavedCityWeather> = mutableListOf()
    var citiesInWatchList: MutableList<SavedCityWeather> = _citiesInWatchList

    private var _weatherOfTopCity: CityAllWeatherData? = null
    val weatherOfTopCity: CityAllWeatherData? = _weatherOfTopCity


    fun getData() {
        fetchWatchListCitiesFromDataBase()
    }

    private fun fetchWatchListCitiesFromDataBase() {
        daoDatabase.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CitySingleObserver<List<SavedCityWeather>>(compositeDisposable) {
                override fun onSuccess(list: List<SavedCityWeather>) {
                    if (list.isEmpty()) {
                        appShouldNavigateToSearchFragment()
                    } else {
                        _citiesInWatchList = list.toMutableList()
                        setCityNameAndCountryName(_citiesInWatchList[0])
                        getTopCityWeatherFromServer(_citiesInWatchList[0])
                    }
                }
            })
    }

    private fun appShouldNavigateToSearchFragment() {
        _mustNavigateToSearchFragment.value = true
    }

    private fun setCityNameAndCountryName(savedCityWeather: SavedCityWeather) {
        cityName = savedCityWeather.cityName
        countryName = savedCityWeather.country
    }

    private fun getTopCityWeatherFromServer(savedCityWeather: SavedCityWeather) {
        apiService.getCityWeather(
            savedCityWeather.lat,
            savedCityWeather.lon,
            "metric",
            MainActivity.API_KEY
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CitySingleObserver<CityAllWeatherData>(compositeDisposable) {
                override fun onSuccess(cityWeatherAllInformation: CityAllWeatherData) {
                    _weatherOfTopCity = cityWeatherAllInformation
                    addCityToWatchList()
                }
            })
    }

    private fun addCityToWatchList() {
        val newSavedCityWeather = SavedCityWeather.convertCityWeatherAllDataToWatchListWeather(
            cityName,
            countryName,
            _weatherOfTopCity!!
        )
        for (i in _citiesInWatchList.indices) {
            if (newSavedCityWeather.cityName == _citiesInWatchList[i].cityName) {
                _citiesInWatchList[i] = newSavedCityWeather
            }
        }
        daoDatabase.addCity(newSavedCityWeather)
    }


    fun uiEvent(event: CityFragmentEvent) {
        when (event) {
            is CityFragmentEvent.LandingViewAnimationEnd -> {
                _mustIntroLottieAnimationShow.value = false
            }
        }
    }


    fun getAllCitiesInWatchList(): ArrayList<SavedCityWeather> {
        if (citiesInWatchList != null) {
            val watchList = arrayListOf<SavedCityWeather>()
            watchList.addAll(citiesInWatchList!!)
            return watchList
        }
        return arrayListOf()
    }

    fun getHourlyWeather(): List<HourlyWeather> {
        val DAY_TO_MILLI_SECOND = 86452
        val MAXIMUM_NUMBER_WEATHER = 23

        val cityHourlyWeather = weatherOfTopCity?.hourly ?: listOf()

        var i = 0
        while (i < cityHourlyWeather.size && i < MAXIMUM_NUMBER_WEATHER) {
            val weatherHour: HourlyWeather = cityHourlyWeather[i]
            val sunrise: Long = (weatherOfTopCity?.current?.sunrise ?: 0L) as Long
            val sunset: Long = (weatherOfTopCity?.current?.sunset ?: 0L) as Long
            val dt: Int = weatherHour.dt
            if (dt > sunrise && dt > sunset) {
                cityHourlyWeather[i].setImage(
                    sunrise + DAY_TO_MILLI_SECOND,
                    sunset + DAY_TO_MILLI_SECOND
                )
            } else {
                cityHourlyWeather[i].setImage(sunrise, sunset)
            }
            ++i
        }
        return weatherOfTopCity?.hourly ?: listOf()
    }

    fun getDailyWeather(): List<DailyWeather> {
        return weatherOfTopCity?.daily ?: listOf()
    }

    fun getCityNameWithCountry(): String = "$cityName, $countryName"

    fun onRefreshData() {
        getData()
    }

    fun deleteWatchList() {
        if (wantToDeleteCity != null) {
            citiesInWatchList.remove(wantToDeleteCity)
            daoDatabase.deleteCity(wantToDeleteCity!!)
        }
    }

    fun addCityToDatabaseFromSearchFragment(savedCityWeather: SavedCityWeather) {
        setCityNameAndCountryName(savedCityWeather)
        viewModelScope.launch(Dispatchers.IO) {
            daoDatabase.addCity(savedCityWeather)
        }
    }
}

