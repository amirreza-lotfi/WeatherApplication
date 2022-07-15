package com.amirreza.presentation.weatherapplication.base

import android.content.Context
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment

abstract class NonRefreshableWeatherFragment: Fragment(), NonRefreshAbleWeatherView {
    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout
    override val viewContext: Context?
        get() = context
}