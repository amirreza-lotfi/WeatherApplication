package com.amirreza.domain.repository

import com.amirreza.domain.entity.OneCallWeatherEntitys.CityWeatherAllInformation
import com.amirreza.presentation.weatherapplication.MainActivity
import io.reactivex.Single

interface WeatherService {
    fun getCityWeather(lat:Double, lon:Double, unit:String,appId:String): Single<CityWeatherAllInformation>
}