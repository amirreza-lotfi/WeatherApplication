package com.amirreza.domain.repository

import com.amirreza.domain.entity.OneCallWeatherEntitys.CityWeatherAllInformation
import io.reactivex.Single

interface WeatherService {
    fun getCityWeather(): Single<CityWeatherAllInformation>
}