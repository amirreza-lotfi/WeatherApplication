package com.amirreza.presentation.weatherapplication.base

import android.animation.Animator
import android.util.Log

abstract class CityLandingAnimationListeners: Animator.AnimatorListener{
    override fun onAnimationCancel(p0: Animator?) {
        Log.i("animation","animation is canceled")
    }

    override fun onAnimationRepeat(p0: Animator?) {
        Log.i("animation","animation is repeating")
    }
}

