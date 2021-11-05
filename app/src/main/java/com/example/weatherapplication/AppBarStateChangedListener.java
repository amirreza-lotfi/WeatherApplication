package com.example.weatherapplication;

import android.util.Log;

import com.google.android.material.appbar.AppBarLayout;

public abstract class AppBarStateChangedListener implements AppBarLayout.OnOffsetChangedListener {

    public enum State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private State mCurrentState = State.IDLE;


    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout,int verticalOffset) {
        Log.i("offset",verticalOffset+" "+appBarLayout.getTotalScrollRange());
        if (verticalOffset == 0) {
            setCurrentStateAndNotify(appBarLayout, State.EXPANDED);
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            setCurrentStateAndNotify(appBarLayout, State.COLLAPSED);
        } else {
            setCurrentStateAndNotify(appBarLayout, State.IDLE);
        }
    }

    private void setCurrentStateAndNotify(AppBarLayout appBarLayout, State state){
        if (mCurrentState != state) {
            onStateChanged(appBarLayout, state);
        }
        mCurrentState = state;
    }

    public abstract void onStateChanged(AppBarLayout appBarLayout, State state);
}
