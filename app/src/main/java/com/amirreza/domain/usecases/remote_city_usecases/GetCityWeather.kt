package com.amirreza.domain.usecases.remote_city_usecases

import com.amirreza.domain.entity.CityAllWeatherDataEntity.CityAllWeatherData
import com.amirreza.domain.entity.CityAllWeatherDataEntity.CurrentWeather
import com.amirreza.domain.repository.WeatherService
import com.amirreza.presentation.weatherapplication.MainActivity
import io.reactivex.Single

class GetCityWeather(private val weatherService: WeatherService){
    operator fun invoke(lat:Double, lon:Double, unit:String): Single<CityAllWeatherData> {
        return weatherService.getCityWeather(lat,lon,unit,MainActivity.API_KEY)
    }
}

