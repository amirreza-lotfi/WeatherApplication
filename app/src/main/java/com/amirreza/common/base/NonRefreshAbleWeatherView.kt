package com.amirreza.common.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.amirreza.weatherapplication.R

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
                    opsLayout = LayoutInflater.from(viewContext)
                        .inflate(R.layout.fragment_ops,root,false)
                    root.addView(opsLayout)
                }
                opsLayout?.visibility = if(mustShow) View.VISIBLE else View.GONE
            }
        }
    }
}