package com.amirreza.presentation.weatherapplication.CityFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amirreza.domain.entity.CityAllWeatherDataEntity.CityAllWeatherData
import com.amirreza.domain.entity.CityAllWeatherDataEntity.DailyWeather
import com.amirreza.domain.entity.CityAllWeatherDataEntity.HourlyWeather
import com.amirreza.domain.entity.SavedCityWeather
import com.amirreza.common.base.CitySingleObserver
import com.amirreza.common.base.WeatherViewModel
import com.amirreza.common.base.asyncRequest
import com.amirreza.domain.usecases.CityWeatherUseCase
import com.amirreza.domain.usecases.WatchListUsecase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import java.util.*

class CityFragmentViewModel(
    private val cityWeatherUC: CityWeatherUseCase,
    private val watchListUC: WatchListUsecase
) : WeatherViewModel() {

    var cityName: String = ""
    var countryName: String = ""
        private set

    val transactionToDialogFragment = MutableLiveData(false)

    private var wantToDeleteCity: SavedCityWeather? = null

    private var _citiesInWatchList= MutableLiveData<ArrayList<SavedCityWeather>>(arrayListOf())
    var citiesInWatchList: LiveData<ArrayList<SavedCityWeather>> = _citiesInWatchList

    private var _weatherOfTopCity = MutableLiveData<CityAllWeatherData>()
    val weatherOfTopCity: MutableLiveData<CityAllWeatherData> = _weatherOfTopCity


    fun getData() {
        _citiesInWatchList.value.let {
            it?.clear()
        }
        fetchWatchListCitiesFromDataBase()
    }

    private fun fetchWatchListCitiesFromDataBase() {
        setProgressBarVisibility(true)
        watchListUC.getAllSavedCities()
            .asyncRequest()
            .subscribe(object : CitySingleObserver<List<SavedCityWeather>>(compositeDisposable) {
                override fun onSuccess(list: List<SavedCityWeather>) {
                    _citiesInWatchList.value!!.addAll(list)
                    setOpsViewVisibility(false)
                    setCityNameAndCountryName(list[0])
                    getTopCityWeatherFromServer(list[0])
                }
                override fun onError(e: Throwable) {
                    setOpsViewVisibility(true)
                }
            })
    }

    fun setCityNameAndCountryName(savedCityWeather: SavedCityWeather) {
        cityName = savedCityWeather.cityName
        countryName = savedCityWeather.country
    }

    private fun getTopCityWeatherFromServer(savedCityWeather: SavedCityWeather) {
        cityWeatherUC.getCityWeather(savedCityWeather.lat,savedCityWeather.lon)
            .asyncRequest()
            .doFinally {setProgressBarVisibility(false)}
            .subscribe(object : CitySingleObserver<CityAllWeatherData>(compositeDisposable) {
                override fun onSuccess(cityWeatherAllInformation: CityAllWeatherData) {
                    _weatherOfTopCity.value = cityWeatherAllInformation
                    addCityToWatchList()
                }

                override fun onError(e: Throwable) {
                    setOpsViewVisibility(true)
                }
            })
    }

    private fun addCityToWatchList() {
        val newSavedCityWeather = SavedCityWeather.convertCityWeatherAllDataToWatchListWeather(
            cityName,
            countryName,
            _weatherOfTopCity.value!!
        )
        for (i in _citiesInWatchList.value!!.indices) {
            if (newSavedCityWeather.cityName == _citiesInWatchList.value!![i].cityName) {
                _citiesInWatchList.value!!.set(i,newSavedCityWeather)
                break
            }
        }
        watchListUC.addCityToWatchList(newSavedCityWeather)
    }



    fun getHourlyWeather(): List<HourlyWeather> {
        val DAY_TO_MILLI_SECOND = 86452
        val MAXIMUM_NUMBER_WEATHER = 23

        val cityHourlyWeather = _weatherOfTopCity.value?.hourly ?: listOf()

        var i = 0
        while (i < cityHourlyWeather.size && i < MAXIMUM_NUMBER_WEATHER) {
            val weatherHour: HourlyWeather = cityHourlyWeather[i]
            val sunrise = (_weatherOfTopCity.value?.current?.sunrise ?: 0)
            val sunset = (_weatherOfTopCity.value?.current?.sunset ?: 0)
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
        return _weatherOfTopCity.value?.hourly ?: listOf()
    }

    fun getDailyWeather(): List<DailyWeather> {
        return _weatherOfTopCity.value?.daily ?: listOf()
    }

    fun getCityNameWithCountry(): String = "$cityName, $countryName"

    fun onRefreshData() {
        getData()
    }

    fun updateCityInWatchList(savedCityWeather: SavedCityWeather){
        watchListUC.addCityToWatchList(
            savedCityWeather.copy(createdTime = Calendar.getInstance().time.time)
        )
        onRefreshData()
    }

    fun deleteWatchList() {
        if (wantToDeleteCity != null) {
            _citiesInWatchList.value!!.remove(wantToDeleteCity)
            watchListUC.deleteCityFromWatchList(wantToDeleteCity!!)
        }
    }

    private fun setProgressBarVisibility(mustShow:Boolean){
        progressBarLiveData.value = mustShow
    }

    private fun setOpsViewVisibility(mustShow:Boolean){
        opsViewVisibility.value = mustShow
    }
}

