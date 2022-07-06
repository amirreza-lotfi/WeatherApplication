package com.amirreza.presentation.weatherapplication.CityFragment

import android.animation.Animator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amirreza.domain.entity.CityAllWeatherDataEntity.CityAllWeatherData
import com.amirreza.domain.entity.TimeProcess
import com.amirreza.domain.entity.WatchListWeather
import com.amirreza.presentation.weatherapplication.DialogFragmentOfWatchList.DialogFragmentWatchList
import com.amirreza.presentation.weatherapplication.DialogFragmentOfWatchList.util.OnDialogActions
import com.amirreza.presentation.weatherapplication.DialogFragmentOfWatchList.util.OnItemClickCallBackWatchList
import com.amirreza.presentation.weatherapplication.WatchList.WatchListCityAdapter
import com.amirreza.presentation.weatherapplication.base.CityLandingAnimationListeners
import com.amirreza.presentation.weatherapplication.base.RefreshableWeatherFragment
import com.amirreza.weatherapplication.R
import com.amirreza.weatherapplication.databinding.FragmentCityBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class CityFragment : RefreshableWeatherFragment(), OnItemClickCallBackWatchList{

    private lateinit var binding: FragmentCityBinding
    private val cityFragmentViewModel:CityFragmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val landingView = binding.viewLanding

        cityFragmentViewModel.mustIntroLottieAnimationShow.observe(viewLifecycleOwner){ mustShow ->
            if(mustShow){
                landingView.visibility = VISIBLE
                landingView.addAnimatorListener(object : CityLandingAnimationListeners() {
                    override fun onAnimationStart(p0: Animator?) {
                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        cityFragmentViewModel.uiEvent(CityFragmentEvent.LandingViewAnimationEnd)
                    }
                })
            }else{
                super.setProgressBarIndicator(mustShow)
                cityFragmentViewModel.getData()
            }
        }

        cityFragmentViewModel.mustNavigateToSearchFragment.observe(viewLifecycleOwner){ mustNavigate->

            if(mustNavigate){
                findNavController(view).navigate(R.id.action_cityFragment_to_searchFragment)
            }else{
                fillCityFragmentContent()
            }
        }


        cityFragmentViewModel.transactionToDialogFragment.observe(viewLifecycleOwner){ isItemSelected ->
            if(isItemSelected){
                cityFragmentViewModel.deleteWatchList()
                findNavController(view).navigate(R.id.action_cityFragment_to_dialogFragmentWatchList)
            }
        }

        setToolbarAndCollapsingToolbarLayout()
        setDrawerNavigationView()
        setRefreshFragment()
    }

    private fun fillCityFragmentContent(){
        cityFragmentViewModel.hasProblemInGettingDataFromServer.observe(viewLifecycleOwner){ mustShow ->
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
        setUpWatchListAdapter()
        setHourlyWeatherAdapter()
        setDailyWeather()
        super.setProgressBarIndicator(false)
    }

    private fun setCurrentWeather() {
        val cityWeather: CityAllWeatherData = cityFragmentViewModel.weatherOfTopCity!!

        binding.cityFragmentWeatherImage.setImageResource(cityWeather.getWeatherImagePath())
        binding.cityFragmentTemperatureCurrent.text = cityWeather.getCurrentTemperatureWithCelsius()
        binding.cityFragmentFeelLike.text = cityWeather.getCurrentFillLikeWithCelsius()
        binding.cityFragmentDescription.text = cityWeather.current.timeDescription
        binding.cityFragmentCityName.text = cityFragmentViewModel.getCityNameWithCountry()
        binding.tvPressureText.text = "${cityWeather.current.pressure} hPa"
        binding.tvHummidityValue.text = "${cityWeather.current.humidity}%"
        binding.tvWindValue.text = "${cityWeather.current.wind_speed} km/h"

        val timeProcess = TimeProcess(cityWeather.current.dt.toLong())
        binding.cityFragmentUpdatingTime.text = timeProcess.dateIn_MONTH_DAY_YEAR_format
        binding.tvDailyRangeText.text = cityWeather.getRangeOfCurrentTemperature()

        binding.cityFragmentCol.isTitleEnabled = true
        binding.cityFragmentCol.title = cityFragmentViewModel.countryName
    }
    private fun setUpWatchListAdapter(){
        val watchListAdapter = WatchListCityAdapter(cityFragmentViewModel.getAllCitiesInWatchList(),this)
        binding.cityFragmentWatchListRecyclerView.adapter = watchListAdapter
        binding.cityFragmentWatchListRecyclerView.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
    }
    private fun setHourlyWeatherAdapter(){
        val hourlyWeatherAdapter = HourlyWeatherAdapter(cityFragmentViewModel.getHourlyWeather())
        binding.cityFragmentHourlyWeatherContainer.adapter = hourlyWeatherAdapter
        binding.cityFragmentHourlyWeatherContainer.layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
        solveRecyclerViewScroll(binding.cityFragmentHourlyWeatherContainer)
    }
    private fun setDailyWeather(){
        val dailyWeatherAdapter = DailyWeatherAdapter(cityFragmentViewModel.getDailyWeather())
        binding.cityFragmentRecyclerViewDaily.layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
        binding.cityFragmentRecyclerViewDaily.adapter = dailyWeatherAdapter
        solveRecyclerViewScroll(binding.cityFragmentRecyclerViewDaily)
    }


    private fun setToolbarAndCollapsingToolbarLayout() {
        binding.cityFragmentToolbar.setNavigationIcon(R.drawable.ic_menu_24)
        binding.appBarLayout.setExpanded(true)
        binding.cityFragmentCol.scrimAnimationDuration = 500
        val appCompatActivity = activity as AppCompatActivity?

        appCompatActivity!!.setSupportActionBar(binding.cityFragmentToolbar)
        binding.cityFragmentCol.setExpandedTitleColor(
            ContextCompat.getColor(
                requireContext(),
                android.R.color.transparent
            )
        )
    }

    private fun setDrawerNavigationView() {
        val watchListCity = ActionBarDrawerToggle(activity,binding.cityFragmentDrawerLayout,binding.cityFragmentToolbar, R.string.open, R.string.close)
        binding.cityFragmentDrawerLayout.addDrawerListener(watchListCity)
        binding.cityFragmentDrawerLayout.addDrawerListener(object : DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {
                setRefreshLayout(false)
            }

            override fun onDrawerClosed(drawerView: View) {
                setRefreshLayout(true)
            }

            override fun onDrawerStateChanged(newState: Int) {}
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    fun solveRecyclerViewScroll(recyclerView: RecyclerView) {
        recyclerView.setOnTouchListener { v: View?, event: MotionEvent ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                setRefreshLayout(false)
            } else if (event.action == MotionEvent.ACTION_UP) {
                setRefreshLayout(true)
            }
            false
        }
    }
    fun setRefreshLayout(enable:Boolean){
        binding.cityFragmentSwiperRefresh.isEnabled = enable
    }

    private fun setRefreshFragment() {
        binding.cityFragmentSwiperRefresh.setOnRefreshListener {
            binding.cityFragmentDrawerLayout.closeDrawer(GravityCompat.START)
            cityFragmentViewModel.onRefreshData()
            binding.cityFragmentSwiperRefresh.isRefreshing = false
        }
    }

    override fun onClickWatchListItem(watchListWeather: WatchListWeather) {
//        cityFragmentViewModel.setSearchRequestCityInSearchFragment(
//            CityEntity(watchListWeather.toString(),watchListWeather.lon.toString())
//        )
//        setRefreshFragment()
    }

    override fun onLongClickWatchListItem(watchListWeather: WatchListWeather) {
        DialogFragmentWatchList(object: OnDialogActions{
            override fun onDeleteClicked(watchListWeather: WatchListWeather) {
                cityFragmentViewModel.deleteWatchList()
            }

            override fun onCancelClicked(watchListWeather: WatchListWeather) {
                TODO("Not yet implemented")
            }
        },watchListWeather).showsDialog
    }
}