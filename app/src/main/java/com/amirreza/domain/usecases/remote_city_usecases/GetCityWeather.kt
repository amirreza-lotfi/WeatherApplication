package com.amirreza.domain.usecases.remote_city_usecases

import android.util.Log
import com.amirreza.common.getImageRecourse
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
            .map { city ->
                val currentWeather = city.current.copy(
                    picturePath = getImageRecourse(city.current.weather[0].description,city.current.weather[0].description,city.current.temp),
                    timeDescription = if (city.current.dt in city.current.sunrise..city.current.sunset) "day" else "night"
                )
                val dailyWeather = mutableListOf<DailyWeather>()
                for (daily: DailyWeather in city.daily) {
                    dailyWeather.add(
                        daily.copy(
                            imagePath = getImageRecourse(
                                daily.weather[0].description,
                                "day",
                                daily.temp.day
                            )
                        )
                    )
                }

                val currentDay = city.daily[0]

                val hourlyWeather = mutableListOf<HourlyWeather>()
                for (hourly: HourlyWeather in city.hourly) {
                    hourlyWeather.add(
                        hourly.copy(
                            imagePath = if (hourly.dt in currentDay.sunrise..currentDay.sunset)
                                getImageRecourse(hourly.weather[0].description, "day", hourly.temp)
                            else getImageRecourse(hourly.weather[0].description, "night", hourly.temp)
                        )
                    )
                }

                return@map city.copy(
                    current = currentWeather,
                    daily = dailyWeather,
                    hourly = hourlyWeather
                )
            }
    }
}

