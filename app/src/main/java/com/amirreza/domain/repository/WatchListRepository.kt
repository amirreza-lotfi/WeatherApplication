package com.amirreza.domain.repository

import com.amirreza.domain.entity.WatchListWeather

interface WatchListRepository {
    fun addCityToWatchList(watchListWeather: WatchListWeather)
    fun deleteCityFromWatchList(watchListWeather: WatchListWeather)
    fun getNumberOfCityInDatabase():Int
}