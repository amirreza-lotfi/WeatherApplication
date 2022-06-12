package com.amirreza.domain.entity

import com.amirreza.domain.entity.OneCallWeatherEntitys.Alert
import com.amirreza.domain.entity.OneCallWeatherEntitys.CityHourlyWeather
import com.amirreza.domain.entity.OneCallWeatherEntitys.WeatherDescription
import com.google.gson.annotations.SerializedName

data class CityWeather(
    val alerts: List<Alert>,
    val clouds: Int,
    val daily: List<CityDailyWeather>,

    @SerializedName("dew_point")
    val AtmosphericTemperature: Double,

    @SerializedName("dt")
    val requestTime: Int,

    @SerializedName("feels_like")
    val feelsLike: Double,

    val cityHourlyWeather: List<CityHourlyWeather>,
    val humidity: Int,
    val pressure: Int,
    val sunrise: Int,
    val sunset: Int,

    @SerializedName("temp")
    val temperature: Double,

    /**
     *  The maximum value of UV index for the day
     * */
    @SerializedName("uvi")
    val maxUV: Double,

    val visibility: Int,

    @SerializedName("weather")
    val weatherDescription: List<WeatherDescription>,

    /**
     * Wind direction, degrees (meteorological
     * */
    val wind_deg: Int,

    @SerializedName("wind_speed")
    val windSpeed: Double,
    var picturePath: Int = 0,
    var timeDescription: String = ""
){
    init {
        setTimeDescription()
        setPicturePath()
    }

    private fun setTimeDescription() {
        timeDescription = if (requestTime >= sunrise && requestTime <= sunset) "day" else "night"
    }
    private fun setPicturePath(){
        picturePath = WeatherImage.getImageRecourse(weatherDescription[0].description,timeDescription,temperature)
    }
}