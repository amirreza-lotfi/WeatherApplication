package com.amirreza.presentation.weatherapplication.DialogFragmentOfWatchList.util

import com.amirreza.domain.entity.SavedCityWeather

interface OnItemClickCallBackWatchList {
    fun onClickWatchListItem(savedCityWeather: SavedCityWeather)
    fun onLongClickWatchListItem(savedCityWeather: SavedCityWeather)
}