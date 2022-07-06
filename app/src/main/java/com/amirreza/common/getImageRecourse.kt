package com.amirreza.common

import com.amirreza.weatherapplication.R
import java.util.*

fun getImageRecourse(description: String, time: String, temp: Double): Int {
    var description = description
    description = description.lowercase(Locale.getDefault())
    //thunderstorm
    if (description == "thunderstorm with light rain") {
        return if (time == "night") R.drawable.ic_thunderstorm_no_rain_night else R.drawable.ic_thunderstorm_no_rain
    } else if (description == "thunderstorm with rain") {
        return if (time == "night") R.drawable.ic_thunderstorm_rain_night else R.drawable.ic_thunderstorm_rain
    } else if (description == "thunderstorm with heavy rain") {
        return if (time == "night") R.drawable.ic_thunderstorm_rain_night else R.drawable.ic_thunderstorm_rain
    } else if (description == "light thunderstorm") {
        return if (time == "night") R.drawable.ic_thunderstorm_no_rain_night else R.drawable.ic_thunderstorm_no_rain
    } else if (description == "thunderstorm") {
        return if (time == "night") R.drawable.ic_thunderstorm_no_rain_night else R.drawable.ic_thunderstorm_no_rain
    } else if (description == "heavy thunderstorm") {
        return if (time == "night") R.drawable.ic_thunderstorm_no_rain_night else R.drawable.ic_thunderstorm_no_rain
    } else if (description == "ragged thunderstorm") {
        return if (time == "night") R.drawable.ic_thunderstorm_no_rain_night else R.drawable.ic_thunderstorm_no_rain
    } else if (description == "thunderstorm with light drizzle") {
        return if (time == "night") R.drawable.ic_thunderstorm_no_rain_night else R.drawable.ic_thunderstorm_no_rain
    } else if (description == "thunderstorm with drizzle") {
        return if (time == "night") R.drawable.ic_thunderstorm_no_rain_night else R.drawable.ic_thunderstorm_no_rain
    } else if (description == "thunderstorm with heavy drizzle") {
        return if (time == "night") R.drawable.ic_thunderstorm_rain_night else R.drawable.ic_thunderstorm_rain
    } else if (description == "light intensity drizzle") {
        return if (time == "night") R.drawable.ic_drizzle_night else R.drawable.ic_drizzle
    } else if (description == "drizzle") {
        return if (time == "night") R.drawable.ic_drizzle_night else R.drawable.ic_drizzle
    } else if (description == "heavy intensity drizzle") {
        return if (time == "night") R.drawable.ic_rain_drizzle_night else R.drawable.ic_rain_drizzle
    } else if (description == "light intensity drizzle rain") {
        return if (time == "night") R.drawable.ic_drizzle_night else R.drawable.ic_drizzle
    } else if (description == "drizzle rain") {
        return if (time == "night") R.drawable.ic_rain_drizzle_night else R.drawable.ic_rain_drizzle
    } else if (description == "heavy intensity drizzle rain") {
        return if (time == "night") R.drawable.ic_rain_drizzle_night else R.drawable.ic_rain_drizzle
    } else if (description == "shower rain and drizzle") {
        return if (time == "night") R.drawable.ic_rain_drizzle_night else R.drawable.ic_rain_drizzle
    } else if (description == "heavy shower rain and drizzle") {
        return if (time == "night") R.drawable.ic_rain_drizzle_night else R.drawable.ic_rain_drizzle
    } else if (description == "shower drizzle") {
        return if (time == "night") R.drawable.ic_rain_drizzle_night else R.drawable.ic_rain_drizzle
    } else if (description == "light rain") {
        return if (time == "night") R.drawable.ic_drizzle_night else R.drawable.ic_drizzle
    } else if (description == "moderate rain") {
        return if (time == "night") R.drawable.ic_heavy_rain_night else R.drawable.ic_heavy_rain
    } else if (description == "heavy intensity rain") {
        return if (time == "night") R.drawable.ic_heavy_rain_night else R.drawable.ic_heavy_rain
    } else if (description == "very heavy rain") {
        return if (time == "night") R.drawable.ic_heavy_rain_night else R.drawable.ic_heavy_rain
    } else if (description == "extreme rain") {
        return if (time == "night") R.drawable.ic_heavy_rain_night else R.drawable.ic_heavy_rain
    } else if (description == "freezing rain") {
        if (time == "night") return R.drawable.ic_freezing_rain
    } else if (description == "light intensity shower rain") {
        return if (time == "night") R.drawable.ic_heavy_rain_night else R.drawable.ic_heavy_rain
    } else if (description == "shower rain") {
        return if (time == "night") R.drawable.ic_heavy_rain_night else R.drawable.ic_heavy_rain
    } else if (description == "heavy intensity shower rain") {
        return if (time == "night") R.drawable.ic_heavy_rain_night else R.drawable.ic_heavy_rain
    } else if (description == "ragged shower rain") {
        return if (time == "night") R.drawable.ic_heavy_rain_night else R.drawable.ic_heavy_rain
    } else if (description == "light snow" || description == "snow" || description == "heavy snow" || description == "shower snow" || description == "light shower snow" || description == "heavy shower snow") {
        return if (time == "night") R.drawable.ic_snow_night else R.drawable.ic_snow
    } else if (description == "sleet" || description == "light shower sleet" || description == "shower sleet" || description == "light rain and snow" || description == "rain and snow") {
        return if (time == "night") R.drawable.ic_rain_snow_night else R.drawable.ic_rain_snow
    } else if (description == "clear sky") {
        return if (time == "night") {
            R.drawable.ic_clear_sky_night
        } else if (time == "day" && temp > 45) {
            R.drawable.ic_very_hot
        } else R.drawable.ic_clear_sky
    } else if (description == "few clouds") {
        return if (time == "night") R.drawable.ic_scattered_clouds_night else R.drawable.ic_few_clouds
    } else if (description == "scattered clouds") {
        return if (time == "night") R.drawable.ic_scattered_clouds_night else R.drawable.ic_scattered_clouds
    } else if (description == "broken clouds") {
        return if (time == "night") R.drawable.ic_two_clouds_night else R.drawable.ic_two_clouds
    } else if (description == "overcast clouds") {
        return if (time == "night") R.drawable.ic_two_clouds_night else R.drawable.ic_two_clouds
    } else if (description == "tornado" || description == "squalls") {
        return if (time == "night") R.drawable.ic_windy else R.drawable.ic_windy_night
    } else if (description == "fog" || description == "haze") {
        return if (time == "night") R.drawable.ic_fog_night else R.drawable.ic_fog
    } else if (description == "volcanic ash") {
        return R.drawable.ic_volcanic_ash
    } else if (description == "dust" || description == "sand" || description == "sand/ dust whirls" || description == "smoke") {
        return R.drawable.ic_dust
    }
    return R.drawable.ic_not_founded
}
