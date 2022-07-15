package com.amirreza.domain.usecases.watchlist_usecases

import com.amirreza.domain.repository.WatchListRepository
import io.reactivex.Single

class GetCountOfSavedCity(private val watchListRepository: WatchListRepository) {
    operator fun invoke(): Single<Int> {
        return watchListRepository.getNumberOfCityInDatabase()
    }
}