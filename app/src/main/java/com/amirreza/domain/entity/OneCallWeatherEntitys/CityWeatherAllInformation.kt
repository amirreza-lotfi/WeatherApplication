package com.amirreza.domain.entity.OneCallWeatherEntitys

import com.amirreza.domain.entity.CityWeather
import com.amirreza.domain.entity.WeatherImage
import com.amirreza.presentation.weatherapplication.WatchList.WatchListCityAdapter
import com.google.gson.annotations.SerializedName

data class CityWeatherAllInformation(
    @SerializedName("current")
    val current: CityWeather,

    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
){
    fun getWeatherImagePath():Int{
        return this.current.picturePath
    }
    fun getCurrentTemperatureWithCelsius():String{
        return "${this.current.temperature.toInt()}°"
    }
    fun getCurrentFillLikeWithCelsius():String{
        return "Feel Like : ${this.current.feelsLike.toInt()}°"
    }
    fun getRangeOfCurrentTemperature():String{
        return "${this.current.daily[0].temperature.min}°/ ${this.current.daily[0].temperature.max}°"
    }

}