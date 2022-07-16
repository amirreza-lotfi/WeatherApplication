package com.amirreza.presentation.weatherapplication.CityFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amirreza.domain.entity.CityAllWeatherDataEntity.CityAllWeatherData
import com.amirreza.domain.entity.TimeProcess
import com.amirreza.domain.entity.SavedCityWeather
import com.amirreza.presentation.weatherapplication.DialogFragmentOfWatchList.DialogFragmentWatchList
import com.amirreza.presentation.weatherapplication.DialogFragmentOfWatchList.util.OnDialogActions
import com.amirreza.presentation.weatherapplication.DialogFragmentOfWatchList.util.OnItemClickCallBackWatchList
import com.amirreza.presentation.weatherapplication.CityFragment.WatchList.WatchListCityAdapter
import com.amirreza.weatherapplication.R
import com.amirreza.weatherapplication.databinding.FragmentCityBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class CityFragment : Fragment(){

    private lateinit var binding:FragmentCityBinding
    private val cityFragmentViewModel: CityFragmentViewModel by viewModel()

    private var watchListCityAdapter: WatchListCityAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cityFragmentViewModel.getData()

        cityFragmentViewModel.weatherOfTopCity.observe(viewLifecycleOwner){
            if(it!=null){
                setWeatherData(it)
            }
        }


        cityFragmentViewModel.opsViewVisibility.observe(viewLifecycleOwner) { mustShow ->
            binding.opsFragmentOps.visibility = if(mustShow) View.VISIBLE else View.GONE
        }

        cityFragmentViewModel.progressBarLiveData.observe(viewLifecycleOwner) { mustShow ->
            binding.loadingLayout.visibility = if(mustShow) View.VISIBLE else View.GONE
        }

        setToolbarAndCollapsingToolbarLayout()
        setDrawerNavigationView()
        setNavigationToSearchFragment()
        onRefreshScreen()
    }

    private fun setWeatherData(cityWeather:CityAllWeatherData) {
        setCurrentWeather(cityWeather)
        setUpWatchListAdapter()
        setHourlyWeatherAdapter()
        setDailyWeather()
    }

    private fun setCurrentWeather(cityWeather:CityAllWeatherData) {
        binding.cityFragmentWeatherImage.setImageResource(cityWeather.getWeatherImagePath())
        binding.cityFragmentTemperatureCurrent.text = cityWeather.getCurrentTemperatureWithCelsius()
        binding.cityFragmentFeelLike.text = cityWeather.getCurrentFillLikeWithCelsius()
        binding.cityFragmentDescription.text = cityWeather.current.timeDescription
        binding.cityFragmentCityName.text = cityFragmentViewModel.getCityNameWithCountry()
        binding.tvPressureValue.text = "${cityWeather.current.pressure} hPa"
        binding.tvHummidityValue.text = "${cityWeather.current.humidity}%"
        binding.tvWindValue.text = "${cityWeather.current.wind_speed} km/h"

        val timeProcess = TimeProcess(cityWeather.current.dt.toLong())
        binding.cityFragmentUpdatingTime.text = timeProcess.dateIn_MONTH_DAY_YEAR_format
        binding.tvCityFragmentDailyRange.text = cityWeather.getRangeOfCurrentTemperature()

        binding.cityFragmentCol.isTitleEnabled = true
        binding.cityFragmentCol.title = cityFragmentViewModel.cityName
    }

    private fun setUpWatchListAdapter() {
        val recyclerViewWatchList = binding.cityFragmentWatchListRecyclerView


        cityFragmentViewModel.citiesInWatchList.observe(viewLifecycleOwner){ array->
            watchListCityAdapter?.setCities(array)
        }
        watchListCityAdapter =
            WatchListCityAdapter(
                cityFragmentViewModel.citiesInWatchList.value!!,
                object : OnItemClickCallBackWatchList{
                    override fun onClickWatchListItem(savedCityWeather: SavedCityWeather) {
                        cityFragmentViewModel.updateCityInWatchList(savedCityWeather)
                        closeNavigationDrawer()
                    }
                    override fun onLongClickWatchListItem(savedCityWeather: SavedCityWeather) {
                        DialogFragmentWatchList(object : OnDialogActions {
                            override fun onDeleteClicked(savedCityWeather: SavedCityWeather) {
                                cityFragmentViewModel.deleteFromWatchList(savedCityWeather)
                            }
                        }, savedCityWeather).show(childFragmentManager,"")
                    }
                }
            )
        recyclerViewWatchList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerViewWatchList.adapter = watchListCityAdapter

    }

    private fun setHourlyWeatherAdapter() {
        val hourlyWeatherAdapter = HourlyWeatherAdapter(cityFragmentViewModel.getHourlyWeather())
        binding.cityFragmentHourlyWeatherContainer.adapter = hourlyWeatherAdapter
        binding.cityFragmentHourlyWeatherContainer.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        solveRecyclerViewScroll(binding.cityFragmentHourlyWeatherContainer)
    }

    private fun setDailyWeather() {
        val dailyWeatherAdapter = DailyWeatherAdapter(cityFragmentViewModel.getDailyWeather())
        binding.cityFragmentRecyclerViewDaily.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
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
        closeNavigationDrawer()
        val watchListCity = ActionBarDrawerToggle(
            activity,
            binding.cityFragmentDrawerLayout,
            binding.cityFragmentToolbar,
            R.string.open,
            R.string.close
        )
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

    fun setRefreshLayout(enable: Boolean) {
        binding.cityFragmentSwiperRefresh.isEnabled = enable
    }

    private fun onRefreshScreen() {
        binding.cityFragmentSwiperRefresh.setOnRefreshListener {
            closeNavigationDrawer()
            cityFragmentViewModel.onRefreshData()
            binding.cityFragmentSwiperRefresh.isRefreshing = false
        }
    }

    private fun closeNavigationDrawer(){
        binding.cityFragmentDrawerLayout.closeDrawer(Gravity.START)
    }
    private fun setNavigationToSearchFragment(){
        binding.cityFragmentAddCity.setOnClickListener{
            findNavController().navigate(R.id.action_cityFragment_to_searchFragment)
        }
    }
}