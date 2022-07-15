package com.amirreza.domain.usecases

import com.amirreza.domain.usecases.watchlist_usecases.AddCityToWatchList
import com.amirreza.domain.usecases.watchlist_usecases.DeleteCityFromWatchList
import com.amirreza.domain.usecases.remote_city_usecases.GetCityWeather
import com.amirreza.domain.usecases.watchlist_usecases.GetAllSavedCities
import com.amirreza.domain.usecases.watchlist_usecases.GetCountOfSavedCity

data class WatchListUsecase(
    val addCityToWatchList: AddCityToWatchList,
    val deleteCityFromWatchList: DeleteCityFromWatchList,
    val getAllSavedCities: GetAllSavedCities,
    val getCountOfSavedCity: GetCountOfSavedCity
)