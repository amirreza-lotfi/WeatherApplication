package com.amirreza.data.repository

import com.amirreza.data.configuration.local.WatchListDouInterface
import com.amirreza.domain.entity.SavedCityWeather
import com.amirreza.domain.repository.WatchListRepository
import io.reactivex.Single

class WatchListRepositoryImpl(private val watchListDou: WatchListDouInterface):WatchListRepository {
    override fun getAll(): Single<List<SavedCityWeather>> {
        return watchListDou.allCities
    }

    override fun addCity(savedCityWeather: SavedCityWeather) {
        watchListDou.add(savedCityWeather)
    }

    override fun deleteCity(savedCityWeather: SavedCityWeather) {
        watchListDou.delete(savedCityWeather)

    }
    override fun getNumberOfCityInDatabase(): Single<Int> {
        return watchListDou.numberOfCityInDatabase
    }
}