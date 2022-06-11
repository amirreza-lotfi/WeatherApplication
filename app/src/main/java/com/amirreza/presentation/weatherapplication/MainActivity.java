package com.amirreza.presentation.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.view.View;

import com.amirreza.data.configuration.http.ApiWeatherDataProvider;

public class MainActivity extends AppCompatActivity{
    private ActivityMainBinding binding;

    public static  final String API_KEY = "c21eb0e73605cad86c1b065929ce5bff";//Todo: go to https://api.openweathermap.org/data/2.5/ and create account and get API_KEy
    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);

        initializeMainActivityViewModel();

    }

    public void initializeMainActivityViewModel(){
        mainActivityViewModel = new ViewModelProvider(this,
                new MainActivityViewModelFactory(new CityRepository(ApiWeatherDataProvider.getApiWeatherData(),
                        ApplicationDatabase.getInstance(this).getWatchListDao())))
                .get(MainActivityViewModel.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainActivityViewModel.getRotate().setValue(true);
    }

}