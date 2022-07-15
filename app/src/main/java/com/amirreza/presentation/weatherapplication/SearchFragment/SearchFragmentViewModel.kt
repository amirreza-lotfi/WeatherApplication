package com.amirreza.presentation.weatherapplication.SearchFragment

import com.amirreza.common.base.WeatherViewModel
import com.amirreza.domain.entity.SavedCityWeather
import com.amirreza.domain.usecases.WatchListUsecase

class SearchFragmentViewModel(private val usecase: WatchListUsecase):WeatherViewModel() {
    fun addCityToWatchList(cityName:String, country:String, lat:Double, lon:Double){
        usecase.addCityToWatchList(
            SavedCityWeather(
                cityName = cityName,
                country = country,
                lat = lat,
                lon = lon
            )
        )
    }
}