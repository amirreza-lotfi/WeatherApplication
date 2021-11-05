package com.example.weatherapplication;

import android.util.Log;

import com.example.weatherapplication.WatchList.WatchListDouInterface;
import com.example.weatherapplication.WatchList.WatchListWeather;

import java.util.List;

import io.reactivex.Single;


public class CityRepository {
    private RetrofitWeatherData apiWeatherData;
    private WatchListDouInterface watchListDouInterface;

    public CityRepository(RetrofitWeatherData apiWeatherData, WatchListDouInterface watchListDouInterface) {
        this.apiWeatherData = apiWeatherData;
        this.watchListDouInterface = watchListDouInterface;
    }

    public Single<String> getCityDataFromServer(double lat, double lon){
        Log.i("serverV",lat + " "+ lon);
        return apiWeatherData.getData(lat,lon,"metric",MainActivity.API_KEY);
    }

    public List<WatchListWeather> getCityDataFromDatabase(){
        return watchListDouInterface.getAll();
    }

    public int getNumberOfCityInDatabase(){
        return watchListDouInterface.getNumberOfCityInDatabase();
    }

    public void addWatchListWeather(WatchListWeather watchListWeather){
        watchListDouInterface.add(watchListWeather);
    }

    public void deleteWatchList(WatchListWeather watchListWeather){
        watchListDouInterface.delete(watchListWeather);
    }
}
