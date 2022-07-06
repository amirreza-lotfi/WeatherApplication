package com.amirreza.domain.repository

import com.amirreza.domain.entity.CityAllWeatherDataEntity.CityAllWeatherData
import io.reactivex.Single

interface WeatherService {
    fun getCityWeather(lat:Double, lon:Double, unit:String,appId:String): Single<CityAllWeatherData>
}