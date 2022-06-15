package com.amirreza.presentation.weatherapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.amirreza.weatherapplication.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val rootView: View = binding.root
        setContentView(rootView)
    }

    companion object{
        const val API_KEY = "c21eb0e73605cad86c1b065929ce5bff" //Todo: go to https://api.openweathermap.org/data/2.5/ and create account and get API_KEy
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    }
}