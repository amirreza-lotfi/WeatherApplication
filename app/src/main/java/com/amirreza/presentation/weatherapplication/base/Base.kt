package com.amirreza.presentation.weatherapplication.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.amirreza.weatherapplication.R
import io.reactivex.disposables.CompositeDisposable

open class WeatherViewModel: ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val progressBarLiveData = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

interface NonRefreshAbleWeatherView{
    val rootView: CoordinatorLayout?
    val viewContext: Context?

    fun setProgressBarIndicator(mustShow:Boolean){
        rootView?.let{ coordinatorLayout ->
            viewContext?.let { viewContext ->
                var loadingView = coordinatorLayout.findViewById<View>(R.id.loadingLayout)
                if(loadingView==null && mustShow){
                    loadingView = LayoutInflater.from(viewContext)
                        .inflate(R.layout.loading_layout,coordinatorLayout,false)
                    coordinatorLayout.addView(loadingView)
                }
                loadingView?.visibility = if(mustShow) View.VISIBLE else View.GONE
            }
        }
    }
    fun setOpsLayout(mustShow:Boolean){
        rootView?.let{ root->
            viewContext?.let{ viewContext->
                var opsLayout = root.findViewById<View>(R.id.ops_layout)
                if(opsLayout==null && mustShow){
                    opsLayout = LayoutInflater.from(viewContext).inflate(R.layout.fragment_ops,root,false)
                    root.addView(opsLayout)
                }
                opsLayout?.visibility = if(mustShow) View.VISIBLE else View.GONE
            }
        }
    }
}

interface RefreshableView{
    val rootView: SwipeRefreshLayout?
    val viewContext: Context?

    fun setProgressBarIndicator(mustShow:Boolean){
        rootView?.let{ coordinatorLayout ->
            viewContext?.let { viewContext ->
                var loadingView = coordinatorLayout.findViewById<View>(R.id.loadingLayout)
                if(loadingView==null && mustShow){
                    loadingView = LayoutInflater.from(viewContext)
                        .inflate(R.layout.loading_layout,coordinatorLayout,false)
                    coordinatorLayout.addView(loadingView)
                }
                loadingView?.visibility = if(mustShow) View.VISIBLE else View.GONE
            }
        }
    }
    fun setOpsLayout(mustShow:Boolean){
        rootView?.let{ root->
            viewContext?.let{ viewContext->
                var opsLayout = root.findViewById<View>(R.id.ops_layout)
                if(opsLayout==null && mustShow){
                    opsLayout = LayoutInflater.from(viewContext).inflate(R.layout.fragment_ops,root,false)
                    root.addView(opsLayout)
                }
                opsLayout?.visibility = if(mustShow) View.VISIBLE else View.GONE
            }
        }
    }
}

abstract class NonRefreshableWeatherFragment: Fragment(),NonRefreshAbleWeatherView{
    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout
    override val viewContext: Context?
        get() = context
}
abstract class RefreshableWeatherFragment: Fragment(),RefreshableView{
    override val rootView: SwipeRefreshLayout?
        get() = view as SwipeRefreshLayout
    override val viewContext: Context?
        get() = context
}