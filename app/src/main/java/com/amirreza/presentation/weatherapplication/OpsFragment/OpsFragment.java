package com.amirreza.presentation.weatherapplication.OpsFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.amirreza.presentation.weatherapplication.MainActivityViewModel;
import com.amirreza.weatherapplication.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class OpsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ops, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        MainActivityViewModel mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        MutableLiveData<String> networkError = mainActivityViewModel.getNoConnectionError();


        LottieAnimationView lottieAnimationView = view.findViewById(R.id.ops_fragment_ops);
        lottieAnimationView.playAnimation();
        ExtendedFloatingActionButton extendedFloatingActionButton = view.findViewById(R.id.fab_try_again);
        extendedFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(requireActivity(),R.id.fragmentContainerView).navigate(R.id.action_opsFragment_to_cityFragment2);
                networkError.setValue("Connected");
            }
        });

    }
}