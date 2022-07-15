package com.amirreza.domain.usecases.watchlist_usecases

import com.amirreza.domain.entity.SavedCityWeather
import com.amirreza.domain.repository.WatchListRepository

class DeleteCityFromWatchList(private val watchListRepository: WatchListRepository) {
    operator fun invoke(savedCityWeather: SavedCityWeather){
        return watchListRepository.deleteCity(savedCityWeather)
    }
}