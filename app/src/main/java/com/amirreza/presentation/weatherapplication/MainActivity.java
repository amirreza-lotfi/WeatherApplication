package com.amirreza.presentation.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.view.View;

import com.amirreza.data.configuration.http.ApiWeatherDataProvider;
import com.amirreza.data.configuration.local.ApplicationDatabase;
import com.amirreza.weatherapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity{
    private ActivityMainBinding binding;

    public static  final String API_KEY = "c21eb0e73605cad86c1b065929ce5bff";//Todo: go to https://api.openweathermap.org/data/2.5/ and create account and get API_KEy
    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}