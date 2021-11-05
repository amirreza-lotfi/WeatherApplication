package com.example.weatherapplication.WatchList;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "table_watch_lists")
public class WatchListWeather {
    @PrimaryKey
    @NonNull
    private String cityName;
    private String country;
    private String description;
    private String temperature;
    private String weatherImagePath;
    private double lat;
    private double lon;
    private long createdTime;

    public WatchListWeather(String cityName, String country, String description, String temperature
                                        ,String weatherImagePath, double lat,double lon,long createdTime) {
        this.description = description;
        this.temperature = temperature;
        this.cityName = cityName;
        this.weatherImagePath = weatherImagePath;
        this.lat = lat;
        this.lon = lon;
        this.country = country;
        this.createdTime = createdTime;
    }

    public String getDescription() {
        return description;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getCityName() {
        return cityName;
    }

    public String getWeatherImagePath() {
        return weatherImagePath;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setWeatherImagePath(String weatherImagePath) {
        this.weatherImagePath = weatherImagePath;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getCountry() {
        return country;
    }
}
