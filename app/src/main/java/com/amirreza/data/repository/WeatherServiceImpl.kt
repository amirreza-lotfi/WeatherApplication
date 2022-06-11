package com.amirreza.data.repository

import com.amirreza.domain.entity.OneCallWeatherEntitys.CityWeatherAllInformation
import com.amirreza.domain.repository.WeatherService
import io.reactivex.Single

class WeatherServiceImpl(val apiService: WeatherService):WeatherService {
    override fun getCityWeather(): Single<CityWeatherAllInformation> {
        return apiService.getCityWeather()
    }
}