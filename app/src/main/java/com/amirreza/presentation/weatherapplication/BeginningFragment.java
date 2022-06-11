package com.amirreza.presentation.weatherapplication;

import static android.view.View.GONE;

import android.animation.Animator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.amirreza.weatherapplication.R;

public class BeginningFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beginning, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivityViewModel mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);


        LottieAnimationView lottieAnimationView = view.findViewById(R.id.start_animation);
        lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Log.i("","");
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                lottieAnimationView.cancelAnimation();
                lottieAnimationView.setVisibility(GONE);
                if(mainActivityViewModel.getCityInformation()==null || mainActivityViewModel.getCityInformation().getValue()==null){
                    Navigation.findNavController(view).navigate(R.id.action_beginningFragment_to_searchFragment);
                }else{
                    Navigation.findNavController(view).navigate(R.id.action_beginningFragment_to_cityFragment2);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
}