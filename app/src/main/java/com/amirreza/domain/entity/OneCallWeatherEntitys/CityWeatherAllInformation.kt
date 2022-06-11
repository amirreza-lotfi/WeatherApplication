package com.amirreza.domain.entity.OneCallWeatherEntitys

import com.amirreza.domain.entity.CityWeather
import com.amirreza.domain.entity.WeatherImage
import com.google.gson.annotations.SerializedName

data class CityWeatherAllInformation(
    @SerializedName("current")
    val current: CityWeather,

    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
)