package com.amirreza.domain.entity.CityAllWeatherDataEntity

import com.amirreza.common.getImageRecourse

data class HourlyWeather(
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,
    val feels_like: Double,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val temp: Double,
    val uvi: Double,
    val visibility: Int,
    val weather: List<Weather>,
    val wind_deg: Int,
    val wind_gust: Double,
    val wind_speed: Double,
    var imagePath:Int? = null
){
    fun setImage(sunrise: Long, sunset: Long) {
        imagePath = if (dt in sunrise..sunset)
            getImageRecourse(weather[0].description, "day", temp)
        else getImageRecourse(weather[0].description, "night", temp)
    }
}