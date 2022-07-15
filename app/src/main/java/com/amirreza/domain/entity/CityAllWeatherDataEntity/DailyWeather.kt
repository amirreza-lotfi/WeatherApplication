package com.amirreza.domain.entity.CityAllWeatherDataEntity

import com.amirreza.common.getImageRecourse

import com.google.gson.annotations.SerializedName

data class DailyWeather(
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
    val sunrise: Int,
    val sunset: Int,

    val temp: Temp,

    /**
     *  The maximum value of UV index for the day
     * */
    @SerializedName("uvi")
    val maxUV: Double,

    val weather: List<Weather>,

    /**
     * Wind direction, degrees (meteorological
     * */

    val wind_deg: Int,
    val wind_gust: Double,

    @SerializedName("wind_speed")
    val windSpeed: Double,
    private var imagePath: Int? = null
)