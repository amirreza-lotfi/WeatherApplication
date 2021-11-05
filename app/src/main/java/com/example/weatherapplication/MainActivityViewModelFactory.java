package com.example.weatherapplication;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MainActivityViewModelFactory implements ViewModelProvider.Factory {
    private CityRepository cityRepository;
    public MainActivityViewModelFactory(CityRepository cityRepository){
        this.cityRepository = cityRepository;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(cityRepository);
    }
}
