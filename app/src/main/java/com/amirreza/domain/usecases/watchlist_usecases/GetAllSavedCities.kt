package com.amirreza.domain.usecases.watchlist_usecases

import com.amirreza.domain.entity.SavedCityWeather
import com.amirreza.domain.repository.WatchListRepository
import io.reactivex.Single

class GetAllSavedCities(private val watchListRepository: WatchListRepository) {
    operator fun invoke(): Single<List<SavedCityWeather>> {
        return watchListRepository.getAll()
    }
}