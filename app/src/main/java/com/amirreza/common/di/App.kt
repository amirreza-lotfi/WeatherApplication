package com.amirreza.common.di

import android.app.Application
import androidx.room.Room
import com.amirreza.data.configuration.http.RetrofitWeatherData
import com.amirreza.data.configuration.local.ApplicationDatabase
import com.amirreza.data.configuration.local.WatchListDouInterface
import com.amirreza.data.repository.WatchListRepositoryImpl
import com.amirreza.data.repository.WeatherServiceImpl
import com.amirreza.domain.repository.WatchListRepository
import com.amirreza.domain.repository.WeatherService
import com.amirreza.domain.usecases.CityWeatherUseCase
import com.amirreza.domain.usecases.WatchListUsecase
import com.amirreza.domain.usecases.remote_city_usecases.GetCityWeather
import com.amirreza.domain.usecases.watchlist_usecases.AddCityToWatchList
import com.amirreza.domain.usecases.watchlist_usecases.DeleteCityFromWatchList
import com.amirreza.domain.usecases.watchlist_usecases.GetAllSavedCities
import com.amirreza.domain.usecases.watchlist_usecases.GetCountOfSavedCity
import com.amirreza.presentation.weatherapplication.CityFragment.CityFragmentViewModel
import com.amirreza.presentation.weatherapplication.MainActivity
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class App:Application() {
    override fun onCreate() {
        super.onCreate()

        val repositoriesModule = module {
            single<RetrofitWeatherData> {
                val okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .build()
                val retrofit = Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(MainActivity.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                retrofit.create(RetrofitWeatherData::class.java)
            }
            single<WatchListDouInterface>{
                ApplicationDatabase.getInstance(this@App).watchListDao
            }
            single<WatchListRepository> {
                WatchListRepositoryImpl(get())
            }
            single<WeatherService> {
                WeatherServiceImpl(get())
            }
            single {
                CityWeatherUseCase(GetCityWeather(get()))
            }
            single {
                WatchListUsecase(
                    AddCityToWatchList(get()),
                    DeleteCityFromWatchList(get()),
                    GetAllSavedCities(get()),
                    GetCountOfSavedCity(get())
                )
            }
        }
        val viewModel = module{
            viewModel {
                CityFragmentViewModel(get(),get())
            }
        }

        startKoin {
            androidContext(this@App)
            modules(listOf(repositoriesModule, viewModel))
        }
    }
}