package com.amirreza.presentation.weatherapplication.DialogFragmentOfWatchList.util

import com.amirreza.domain.entity.WatchListWeather

interface OnItemClickCallBackWatchList {
    fun onClick(watchListWeather: WatchListWeather)
    fun onLongClick(watchListWeather: WatchListWeather)
}