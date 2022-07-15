package com.amirreza.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amirreza.domain.entity.CityAllWeatherDataEntity.CityAllWeatherData
import java.util.*

@Entity(tableName = "table_watch_lists")
data class SavedCityWeather(
    @field:PrimaryKey var cityName: String,
    var country: String ="",
    var description: String="",
    var temperature: String ="0.0",
    var weatherImagePath: String = "R.drawable.launcher",
    var lat: Double,
    var lon: Double,
    var createdTime:Long= Calendar.getInstance().time.time
){
    companion object{
        fun convertCityWeatherAllDataToWatchListWeather(city:String,country:String,all:CityAllWeatherData):SavedCityWeather{
            return SavedCityWeather(
                cityName = city,
                country = country,
                description =  all.current.timeDescription!!,
                temperature = all.current.temp.toString(),
                weatherImagePath = all.getWeatherImagePath().toString(),
                lat = all.lat,
                lon = all.lon,
            )
        }
    }
}