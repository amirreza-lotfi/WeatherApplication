package com.amirreza.domain.entity.CityAllWeatherDataEntity

import com.amirreza.common.getImageRecourse

data class CurrentWeather(
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val uvi: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind_deg: Int,
    val wind_speed: Double,
    var picturePath: Int = 0,
    var timeDescription: String = ""
){
    init {
        setTimeDescription()
        setPicturePath()
    }

    private fun setTimeDescription() {
        timeDescription = if (dt in sunrise..sunset) "day" else "night"
    }
    private fun setPicturePath(){
        picturePath = getImageRecourse(weather[0].description,timeDescription,temp)
    }
}