package com.amirreza.domain.usecases

import com.amirreza.domain.usecases.remote_city_usecases.GetCityWeather
import com.amirreza.domain.usecases.watchlist_usecases.GetAllSavedCities

data class CityWeatherUseCase(
    val getCityWeather: GetCityWeather
)