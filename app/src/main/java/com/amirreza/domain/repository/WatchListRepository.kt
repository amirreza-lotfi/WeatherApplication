package com.amirreza.domain.repository

import com.amirreza.domain.entity.WatchListWeather
import io.reactivex.Single

interface WatchListRepository {
    fun getAllCities(): Single<List<WatchListWeather>>
    fun addCityToWatchList(watchListWeather: WatchListWeather)
    fun deleteCityFromWatchList(watchListWeather: WatchListWeather)
    fun getNumberOfCityInDatabase():Int
}