package com.amirreza.domain.usecases.watchlist_usecases

import com.amirreza.domain.repository.WatchListRepository

class GetCountOfSavedCity(private val watchListRepository: WatchListRepository) {
    operator fun invoke(): Int {
        return watchListRepository.getNumberOfCityInDatabase()
    }
}