package com.amirreza.domain.entity.OneCallWeatherEntitys

import com.amirreza.domain.entity.WeatherImage
import com.google.gson.annotations.SerializedName

data class CityHourlyWeather(
    val clouds: Int,
    @SerializedName("dew_point")
    val AtmosphericTemperature: Double,

    @SerializedName("dt")
    val requestTime: Int,

    @SerializedName("feels_like")
    val feelsLike: Double,
    val humidity: Int,
    val pop: Int,
    val pressure: Int,
    val temp: Double,
    val uvi: Double,
    val visibility: Int,

    @SerializedName("weather")
    val weatherDescription: List<WeatherDescription>,
    val wind_deg: Int,
    val wind_gust: Double,
    val wind_speed: Double,
)