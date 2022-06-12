package com.amirreza.presentation.weatherapplication.CityFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amirreza.domain.entity.CityInformation;
import com.amirreza.domain.entity.OneCallWeatherEntitys.CityWeatherAllInformation;
import com.amirreza.presentation.weatherapplication.AppBarStateChangedListener;
import com.amirreza.presentation.weatherapplication.MainActivityViewModel;
import com.amirreza.domain.entity.TimeProcess;
import com.amirreza.presentation.weatherapplication.WatchList.WatchListCityAdapter;
import com.amirreza.domain.entity.WatchListWeather;
import com.amirreza.domain.entity.WeatherImage;

import com.amirreza.weatherapplication.R;

import com.amirreza.weatherapplication.databinding.FragmentCityBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.view.View.GONE;


public class CityFragment extends Fragment{
    private FragmentCityBinding fragmentCityBinding;
    private Toolbar toolbar;
    private String cityName;
    private int weatherImagePath;
    private FrameLayout loadingLayout;
    private SwipeRefreshLayout refreshLayout;
    private DrawerLayout watchListCityDrawerLayout;
    private WatchListCityAdapter watchListCityAdapter;
    private View xmlLayout;
    private RecyclerView watchListRecyclerView;
    private MainActivityViewModel mainActivityViewModel;
    private CityWeatherAllInformation cityWeatherAllData;
    private AppCompatImageView weatherImage;
    private AppCompatTextView temperature;
    private AppCompatTextView feelLike;
    private AppCompatTextView pressure;
    private AppCompatTextView description;
    private TextView cityLocationInCollapsingToolbar;
    private AppCompatTextView humidity;
    private AppCompatTextView wind ;
    private AppCompatTextView updateTime;
    private AppCompatTextView dailyRange;
    private ProgressBar progressBar;
    private RecyclerView dailyWeatherRecyclerView;
    private RecyclerView hourlyWeatherRecyclerView;
    private AppBarLayout appBarLayout;
    private ImageView newCityInWatchList;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private NestedScrollView nestedScrollView;

    private val city
    public CityFragment(){
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        fragmentCityBinding = FragmentCityBinding.inflate(inflater,container,false);
        View rootView = fragmentCityBinding.getRoot();
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        MutableLiveData<String> networkError = mainActivityViewModel.getNoConnectionError();
        networkError.observe(getViewLifecycleOwner(), error -> {
            if(error.equals("No Connection")) {
                showOpsFragment();
            }
        });


        mainActivityViewModel.getCityWeatherDataInOneCallMutableLiveData().observe(getViewLifecycleOwner(), cityWeatherAllData -> {
            this.cityWeatherAllData = cityWeatherAllData;

            if(mainActivityViewModel.getRotate().getValue()){
                setWeatherData(view);
            }
            else if(mainActivityViewModel.getIsNavigateFromSearchFragment().getValue()==true){
                mainActivityViewModel.getIsNavigateFromSearchFragment().setValue(false);
            }
            else{
                if(cityWeatherAllData !=null)
                setWeatherData(view);
            }


        });
        mainActivityViewModel.getWatchListItemSelected().observe(getViewLifecycleOwner(), watchListWeather -> {
            if(!mainActivityViewModel.getIsNavigateFromSearchFragment().getValue() && mainActivityViewModel.getIsWatchListItemSelect().getValue()) {
                setProgressBarVisible();
                closeNavigationView();
                watchListRecyclerView.scrollToPosition(0);
                nestedScrollView.scrollTo(0, 0);
                appBarLayout.setExpanded(true);

                mainActivityViewModel.setCityInformation(watchListWeather.getLat(),
                        watchListWeather.getLon(),
                        watchListWeather.getCityName(),
                        watchListWeather.getCountry());
                mainActivityViewModel.getIsWatchListItemSelect().setValue(false);
                getWeatherDataFromServer(view);
            }
        });
        mainActivityViewModel.getNavigateToDialogDeleteWatchList().observe(getViewLifecycleOwner(), bool -> {
            if(bool)
                Navigation.findNavController(requireActivity(),R.id.fragmentContainerView).navigate(R.id.action_cityFragment2_to_dialogFragmentWatchList);
            else{
                WatchListWeather watchListWeather = mainActivityViewModel.getDeleteWatchList().getValue();
                if(watchListWeather!=null) {
                    watchListCityAdapter.deleteCityFromWatchList(watchListWeather);
                    mainActivityViewModel.deleteWatchList(watchListWeather);
                }
            }
        });



        setToolbarAndCollapsingToolbarLayout();

        setDrawerNavigationView();

        loadCitiesWeatherInNavigationView();

        setProgressBar();
        getWeatherDataFromServer(view);


        refreshFragment(view);

        solveScrollProblem();

        transactionToSearchFragmentIfUserClickOnAddButton(view);

        refreshLayoutListener();


    }

    public void transactionToSearchFragmentIfUserClickOnAddButton(View view){

        newCityInWatchList.setOnClickListener(v -> {
            closeNavigationView();
            Navigation.findNavController(requireActivity(),R.id.fragmentContainerView).navigate(R.id.action_cityFragment2_to_searchFragment);
        });
    }

    public void setToolbarAndCollapsingToolbarLayout(){
        toolbar.setNavigationIcon(R.drawable.ic_menu_24);
        appBarLayout.setExpanded(true);

        collapsingToolbarLayout.setScrimAnimationDuration(500);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);

        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getContext(),android.R.color.transparent));

    }

    public void setDrawerNavigationView(){
        ActionBarDrawerToggle watchListCity = new ActionBarDrawerToggle(getActivity(), watchListCityDrawerLayout, toolbar, R.string.open, R.string.close);
        watchListCityDrawerLayout.addDrawerListener(watchListCity);
        watchListCityDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                disableRefreshLayout();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                enableRefreshLayout();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }

        });
    }

    public void loadCitiesWeatherInNavigationView(){
        List<WatchListWeather> city = mainActivityViewModel.getCityDataFromDatabase();
        watchListCityAdapter = new WatchListCityAdapter( (ArrayList<WatchListWeather>) city,requireActivity());
        watchListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        watchListRecyclerView.setAdapter(watchListCityAdapter);
    }

    public void setProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void getWeatherDataFromServer(View view){

        setProgressBarVisible();
        if(!mainActivityViewModel.getRotate().getValue()){
                mainActivityViewModel.getCityDataFromServer();
        }
    }

    private void setWeatherData(View view){
        setCurrentWeather();
        addCityToWatchList();

        setHourlyWeatherRecyclerView();
        setDailyWeather(view);

        setProgressBarGone();

        mainActivityViewModel.getRotate().setValue(false);
    }

    public void setCurrentWeather(){

        CityInformation cityInformation = mainActivityViewModel.getCityInformation().getValue();
        weatherImage.setImageResource(cityWeatherAllData.getPicturePath());
        weatherImagePath = cityWeatherAllData.getPicturePath();

        StringBuilder temp = new StringBuilder();
        temp.append((int) cityWeatherAllData.getTemperature());
        temp.append("°");
        temperature.setText(temp.toString());

        feelLike.setText(new StringBuilder().append("Feel Like : ").append((int) cityWeatherAllData.getFeelLike()).append("°").toString());
        description.setText(cityWeatherAllData.getDescription());
        cityLocationInCollapsingToolbar.setText(new StringBuilder().append(cityInformation.getCity()).append(", ").append(cityInformation.getCountry()).toString());
        pressure.setText(new StringBuilder().append((int) cityWeatherAllData.getPressure()).append(" hPa").toString());
        humidity.setText(new StringBuilder().append((int) cityWeatherAllData.getHumidity()).append("%").toString());
        wind.setText(new StringBuilder().append(cityWeatherAllData.getWindSpeed()).append(" km/h").toString());

        TimeProcess timeProcess = new TimeProcess(cityWeatherAllData.getTime());
        updateTime.setText(timeProcess.getDateIn_MONTH_DAY_YEAR_format());

        StringBuilder range = new StringBuilder();
        range.append((int) cityWeatherAllData.getDayTemp());
        range.append("°/ ");
        range.append((int) cityWeatherAllData.getNightTemp());
        range.append("°");
        dailyRange.setText(range.toString());

        collapsingToolbarLayout.setTitleEnabled(true);
        collapsingToolbarLayout.setTitle(cityInformation.getCity());

    }

    @SuppressLint("ClickableViewAccessibility")
    public void setHourlyWeatherRecyclerView(){
        final int DAY_TO_MILLI_SECOND = 86452;
        final int MAXIMUM_NUMBER_WEATHER = 23;

        ArrayList<CityHourlyWeather> cityHourlyWeather = cityWeatherAllData.getCityHourlyWeathers();

          for(int i=0;i<cityHourlyWeather.size() && i<MAXIMUM_NUMBER_WEATHER;++i){
                CityHourlyWeather weatherHour = cityHourlyWeather.get(i);
                long sunrise = cityWeatherAllData.getSunrise();
                long sunset = cityWeatherAllData.getSunset();
                long dt = weatherHour.getDataReceivingTime();

                if (dt > sunrise && dt > sunset){
                    cityHourlyWeather.get(i).setImage(sunrise+DAY_TO_MILLI_SECOND,sunset+DAY_TO_MILLI_SECOND);
                }
                else {
                    cityHourlyWeather.get(i).setImage(sunrise,sunset);
                }
          }

        HourlyWeatherAdapter hourlyWeatherAdapter = new HourlyWeatherAdapter(cityHourlyWeather);
        hourlyWeatherRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        hourlyWeatherRecyclerView.setAdapter(hourlyWeatherAdapter);
        solveRecyclerViewScroll(hourlyWeatherRecyclerView);

    }

    @SuppressLint("ClickableViewAccessibility")
    public void setDailyWeather(View view){

        DailyWeatherAdapter dailyWeatherAdapter = new DailyWeatherAdapter(cityWeatherAllData.getCityDailyWeathers());
        dailyWeatherRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false));
        dailyWeatherRecyclerView.setAdapter(dailyWeatherAdapter);
        solveRecyclerViewScroll(dailyWeatherRecyclerView);
    }

    public void refreshFragment(View view){
        refreshLayout.setOnRefreshListener(() -> {
            closeNavigationView();
            getWeatherDataFromServer(view);
            refreshLayout.setRefreshing(false);
        });

    }

    public void setProgressBarGone(){
        loadingLayout.setVisibility(GONE);
    }

    public void setProgressBarVisible(){
        loadingLayout.setVisibility(View.VISIBLE);
    }

    public void solveScrollProblem(){
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangedListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {

                if(state.name().equals("EXPANDED")){
                    enableRefreshLayout();
                }
                else if(state.name().equals("IDLE")) {
                    disableRefreshLayout();
                }
            }
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    public void solveRecyclerViewScroll(RecyclerView recyclerView){
        recyclerView.setOnTouchListener((v, event) -> {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                disableRefreshLayout();
            }
            else if(event.getAction() == MotionEvent.ACTION_UP){
                enableRefreshLayout();
            }
            return false;
        });
    }

    public void addCityToWatchList(){
        String description = cityWeatherAllData.getDescription();
        String timeDescription = cityWeatherAllData.getTimeDescription();
        double temp = cityWeatherAllData.getTemperature();

        CityInformation cityInformation = mainActivityViewModel.getCityInformation().getValue();



        WatchListWeather watchListWeather = new WatchListWeather(
                cityInformation.getCity(),
                cityInformation.getCountry(),
                description,
                String.valueOf(cityWeatherAllData.getTemperature()),
                Integer.toString(WeatherImage.getImageRecourse(description,timeDescription,temp)),
                cityInformation.getLat(),
                cityInformation.getLon(), Calendar.getInstance().getTime().getTime());

        mainActivityViewModel.addWatchListWeather(watchListWeather);
        addCityToWatchListAdapter(watchListWeather);

        List<WatchListWeather> city = mainActivityViewModel.getCityDataFromDatabase();
        watchListCityAdapter.setCities((ArrayList<WatchListWeather>) city);
    }

    public void addCityToWatchListAdapter(WatchListWeather watchListWeather){
        watchListCityAdapter.addWatchListWeather(watchListWeather);
    }

    public void closeNavigationView(){
        watchListCityDrawerLayout.closeDrawer(GravityCompat.START);
    }

    public void refreshLayoutListener(){
        refreshLayout.setOnRefreshListener(() -> {
            getWeatherDataFromServer(xmlLayout);
            refreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onOptionsMenuClosed(@NonNull Menu menu) {
        enableRefreshLayout();
    }

    public void disableRefreshLayout(){
        refreshLayout.setEnabled(false);
    }

    public void enableRefreshLayout(){
        refreshLayout.setEnabled(true);
    }

    public void showOpsFragment(){
        Navigation.findNavController(requireActivity(),R.id.fragmentContainerView).navigate(R.id.action_cityFragment2_to_opsFragment);
    }

}
