package com.amirreza.data.repository

import com.amirreza.domain.entity.OneCallWeatherEntitys.CityWeatherAllInformation
import com.amirreza.domain.repository.WeatherService
import com.amirreza.presentation.weatherapplication.MainActivity
import io.reactivex.Single

class WeatherServiceImpl(private val apiService: WeatherService):WeatherService {
    override fun getCityWeather(lat:Double, lon:Double, unit:String,appId:String): Single<CityWeatherAllInformation> {
        return apiService.getCityWeather(
            lat,lon,unit,appId
        )
    }
}