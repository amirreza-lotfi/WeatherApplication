package com.amirreza.common.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.amirreza.weatherapplication.R

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
    fun setOpsViewVisibility(mustShow:Boolean){
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