package com.amirreza.presentation.weatherapplication.DialogFragmentOfWatchList.util

import com.amirreza.domain.entity.WatchListWeather

interface OnItemClickCallBackWatchList {
    fun onClickWatchListItem(watchListWeather: WatchListWeather)
    fun onLongClickWatchListItem(watchListWeather: WatchListWeather)
}