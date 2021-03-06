package com.amirreza.data.repository

import android.util.Log
import com.amirreza.data.configuration.http.RetrofitWeatherData
import com.amirreza.domain.entity.CityAllWeatherDataEntity.CityAllWeatherData
import com.amirreza.domain.repository.WeatherService
import com.amirreza.presentation.weatherapplication.MainActivity
import io.reactivex.Single

class WeatherServiceImpl(private val apiService: RetrofitWeatherData):WeatherService {
    override fun getCityWeather(lat:Double, lon:Double, unit:String,appId:String): Single<CityAllWeatherData> {
        return apiService.getData(
            lat,lon,unit,appId
        )
    }
}