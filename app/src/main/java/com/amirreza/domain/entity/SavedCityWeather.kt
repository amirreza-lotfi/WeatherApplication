package com.amirreza.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amirreza.domain.entity.CityAllWeatherDataEntity.CityAllWeatherData
import java.util.*

@Entity(tableName = "table_watch_lists")
class WatchListWeather(
    @field:PrimaryKey var cityName: String,
    var country: String,
    var description: String,
    var temperature: String,
    var weatherImagePath: String,
    var lat: Double,
    var lon: Double,
    var createdTime:Long= Calendar.getInstance().time.time
){
    companion object{
        fun convertCityWeatherAllDataToWatchListWeather(city:String,country:String,all:CityAllWeatherData):WatchListWeather{
            return WatchListWeather(
                cityName = city,
                country = country,
                description =  all.current.timeDescription,
                temperature = all.current.temp.toString(),
                weatherImagePath = all.getWeatherImagePath().toString(),
                lat = all.lat,
                lon = all.lon,
            )
        }
    }
}