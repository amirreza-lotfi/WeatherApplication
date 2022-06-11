package com.amirreza.data.configuration.http;

import com.amirreza.presentation.weatherapplication.MainActivity;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiWeatherDataProvider {
    public static RetrofitWeatherData apiWeatherData;

    public static RetrofitWeatherData getApiWeatherData() {
        if(apiWeatherData==null){
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(MainActivity.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            apiWeatherData = retrofit.create(RetrofitWeatherData.class);
        }
        return apiWeatherData;
    }
}
