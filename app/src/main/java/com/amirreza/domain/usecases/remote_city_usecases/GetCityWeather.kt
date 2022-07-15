package com.amirreza.domain.usecases.remote_city_usecases

import com.amirreza.domain.entity.CityAllWeatherDataEntity.CityAllWeatherData
import com.amirreza.domain.entity.CityAllWeatherDataEntity.CurrentWeather
import com.amirreza.domain.entity.CityAllWeatherDataEntity.DailyWeather
import com.amirreza.domain.entity.CityAllWeatherDataEntity.HourlyWeather
import com.amirreza.domain.repository.WeatherService
import com.amirreza.presentation.weatherapplication.MainActivity
import io.reactivex.Single

class GetCityWeather(private val weatherService: WeatherService) {
    operator fun invoke(lat: Double, lon: Double): Single<CityAllWeatherData> {
        return weatherService.getCityWeather(lat, lon, "metric", MainActivity.API_KEY)
            .doAfterSuccess { city ->
                city.current.setTimeDescription()
                city.current.setPicturePath()

                for (daily: DailyWeather in city.daily) {
                    daily.setImage()
                }
                val currentDay = city.daily[0]

                for (hourly: HourlyWeather in city.hourly) {
                    hourly.setImage(currentDay.sunrise.toLong(), currentDay.sunset.toLong())
                }

            }
    }
}

