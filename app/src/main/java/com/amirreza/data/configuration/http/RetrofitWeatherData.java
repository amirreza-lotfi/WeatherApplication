package com.amirreza.data.configuration.http;

import com.amirreza.domain.entity.OneCallWeatherEntitys.CityWeatherAllInformation;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitWeatherData {

    @GET("onecall")
    Single<CityWeatherAllInformation> getData(
            @Query("lat" ) double lat,
            @Query("lon") double lon,
            @Query("units") String unit,
            @Query("appid") String appId
    );
}
