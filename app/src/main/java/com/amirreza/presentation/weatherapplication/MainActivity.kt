package com.amirreza.presentation.weatherapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.amirreza.weatherapplication.R
import com.amirreza.weatherapplication.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    companion object{
        const val API_KEY = "c21eb0e73605cad86c1b065929ce5bff" //Todo: go to https://api.openweathermap.org/data/2.5/ and create account and get API_KEy
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }
}