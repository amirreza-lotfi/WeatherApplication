package com.amirreza.domain.entity.CityAllWeatherDataEntity

data class CityAllWeatherData(
    val current: CurrentWeather,
    val daily: List<DailyWeather>,
    val hourly: List<HourlyWeather>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
){
    fun getWeatherImagePath():Int{
        return this.current.picturePath
    }
    fun getCurrentTemperatureWithCelsius():String{
        return "${this.current.temp.toInt()}°"
    }
    fun getCurrentFillLikeWithCelsius():String{
        return "Feel Like : ${this.current.feels_like.toInt()}°"
    }
    fun getRangeOfCurrentTemperature():String{
        return "${this.daily[0].temp.min}°/ ${this.daily[0].temp.max}°"
    }
}