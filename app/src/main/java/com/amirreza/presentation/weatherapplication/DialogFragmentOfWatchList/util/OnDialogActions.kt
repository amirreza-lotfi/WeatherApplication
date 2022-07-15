package com.amirreza.presentation.weatherapplication.DialogFragmentOfWatchList.util

import com.amirreza.domain.entity.SavedCityWeather

interface OnDialogActions {
    fun onDeleteClicked(savedCityWeather: SavedCityWeather)
    fun onCancelClicked(savedCityWeather: SavedCityWeather)
}