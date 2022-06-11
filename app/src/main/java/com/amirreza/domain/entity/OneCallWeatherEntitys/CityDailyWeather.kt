package com.amirreza.domain.entity

import com.amirreza.domain.entity.OneCallWeatherEntitys.FeelsLike
import com.amirreza.domain.entity.OneCallWeatherEntitys.Temperature
import com.amirreza.domain.entity.OneCallWeatherEntitys.WeatherDescription
import com.google.gson.annotations.SerializedName

data class CityDailyWeather(
    val clouds: Int,

    @SerializedName("dew_point")
    val AtmosphericTemperature: Double,
    val dt: Int,

    @SerializedName("feels_like")
    val feelsLike: FeelsLike,
    val humidity: Int,

    /**
     * 0 and 1 are 'new moon',
     * 0.25 is 'first quarter moon',
     * 0.5 is 'full moon'
     * and 0.75 is 'last quarter moon'.
     * The periods in between are called 'waxing crescent',
     * 'waxing gibbous', 'waning gibous', and 'waning crescent', respectively
     */
    @SerializedName("moon_phase")
    val moonPhase: Double,

    val moonrise: Int,

    @SerializedName("moonset")
    val moonSet: Int,


    /**
     * Probability of precipitation.
     * The values of the parameter vary between 0 and 1,
     * where 0 is equal to 0%, 1 is equal to 100%
     * */
    @SerializedName("pop")
    val probabilityOfPrecipitation: Double,

    val pressure: Int,
    val rain: Double,
    val sunrise: Int,
    val sunset: Int,

    @SerializedName("temp")
    val temperature: Temperature,

    /**
     *  The maximum value of UV index for the day
     * */
    @SerializedName("uvi")
    val maxUV: Double,

    @SerializedName("weather")
    val weatherDescription: List<WeatherDescription>,

    /**
     * Wind direction, degrees (meteorological
     * */
    val wind_deg: Int,

    @SerializedName("wind_speed")
    val windSpeed: Double,
    private var imagePath: Int = 0
){
    init {
        imagePath = WeatherImage.getImageRecourse(
            weatherDescription[0].description,
            "day",
            temperature.day
        )
    }
}