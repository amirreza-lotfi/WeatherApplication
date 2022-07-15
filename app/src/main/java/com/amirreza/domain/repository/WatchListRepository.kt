package com.amirreza.domain.repository

import com.amirreza.domain.entity.SavedCityWeather
import io.reactivex.Single

interface WatchListRepository {
    fun getAll(): Single<List<SavedCityWeather>>
    fun addCity(savedCityWeather: SavedCityWeather)
    fun deleteCity(savedCityWeather: SavedCityWeather)
    fun getNumberOfCityInDatabase():Int
}