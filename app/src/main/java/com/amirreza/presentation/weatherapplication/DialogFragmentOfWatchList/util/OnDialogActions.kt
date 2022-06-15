package com.amirreza.presentation.weatherapplication.DialogFragmentOfWatchList.util

import com.amirreza.domain.entity.WatchListWeather

interface OnDialogActions {
    fun onDeleteClicked(watchListWeather: WatchListWeather)
    fun onCancelClicked(watchListWeather: WatchListWeather)
}