package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import com.example.weatherapplication.CityFragment.ApiWeatherDataProvider;

public class MainActivity extends AppCompatActivity{
    public static  final String API_KEY = "";//Todo: go to https://api.openweathermap.org/data/2.5/ and create account and get API_KEy
    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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