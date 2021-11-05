package com.example.weatherapplication;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitWeatherData {
    @GET("onecall")
    Single<String> getData(
            @Query("lat" ) double lat,
            @Query("lon") double lon,
            @Query("units") String unit,
            @Query("appid") String appId
    );
}
