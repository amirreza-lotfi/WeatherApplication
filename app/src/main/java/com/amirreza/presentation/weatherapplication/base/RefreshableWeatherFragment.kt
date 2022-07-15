package com.amirreza.presentation.weatherapplication.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

abstract class RefreshableWeatherFragment: Fragment(), RefreshableView {
    override val rootView: SwipeRefreshLayout?
        get() = view as SwipeRefreshLayout
    override val viewContext: Context?
        get() = context
}