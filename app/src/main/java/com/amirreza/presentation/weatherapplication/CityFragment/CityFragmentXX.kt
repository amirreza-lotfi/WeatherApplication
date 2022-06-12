package com.amirreza.presentation.weatherapplication.CityFragment

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import com.amirreza.domain.entity.CityInformation
import com.amirreza.domain.entity.OneCallWeatherEntitys.CityWeatherAllInformation
import com.amirreza.domain.entity.TimeProcess
import com.amirreza.presentation.weatherapplication.base.WeatherFragment
import com.amirreza.weatherapplication.R
import com.amirreza.weatherapplication.databinding.FragmentCityBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class CityFragmentXX : WeatherFragment() {

    private lateinit var binding: FragmentCityBinding
    private val cityFragmentViewModel:CityFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.loadingScreen.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                Log.i("", "")
            }
            override fun onAnimationEnd(animator: Animator) {
                binding.loadingScreen.visibility = GONE
                if(cityFragmentViewModel.hasNotAnyCityInDatabase()){
                    findNavController(view).navigate(R.id.action_cityFragment_to_searchFragment)
                }else{
                    fillCityFragmentContent()
                }
            }
            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
    }

    private fun fillCityFragmentContent(){
        cityFragmentViewModel.isInternetConnectionStable.observe(viewLifecycleOwner){ mustShow ->
            if(mustShow){
                super.setProgressBarIndicator(!mustShow)
                super.setOpsLayout(mustShow)
            }else{
                super.setProgressBarIndicator(true)
                setWeatherData()
            }
        }
    }
    private fun setWeatherData() {
        setCurrentWeather()
        addCityToWatchList()
        setHourlyWeatherRecyclerView()
        setDailyWeather(view)
        setProgressBarGone()
        mainActivityViewModel.getRotate().setValue(false)
    }

    private fun setCurrentWeather() {
        val cityWeather: CityWeatherAllInformation = cityFragmentViewModel.weatherOfTopCity!!

        binding.cityFragmentWeatherImage.setImageResource(cityWeather.getWeatherImagePath())
        binding.cityFragmentTemperatureCurrent.text = cityWeather.getCurrentTemperatureWithCelsius()
        binding.cityFragmentFeelLike.text = cityWeather.getCurrentFillLikeWithCelsius()
        binding.cityFragmentDescription.text = cityWeather.current.timeDescription
        binding.cityFragmentCityName.text = cityFragmentViewModel.getCityNameWithCountry()
        binding.tvPressureText.text = "${cityWeather.current.pressure} hPa"
        binding.tvHummidityValue.text = "${cityWeather.current.humidity}%"
        binding.tvWindValue.text = "${cityWeather.current.windSpeed} km/h"

        val timeProcess = TimeProcess(cityWeather.current.requestTime.toLong())
        binding.cityFragmentUpdatingTime.text = timeProcess.dateIn_MONTH_DAY_YEAR_format
        binding.tvDailyRangeText.text = cityWeather.getRangeOfCurrentTemperature()

        binding.cityFragmentCol.isTitleEnabled = true
        binding.cityFragmentCol.title = cityFragmentViewModel.getCountryName()
    }

}