package com.example.weatherapplication.CityFragment;

import com.example.weatherapplication.WeatherImage;

public class CityDailyWeather {
    private double windSpeed;
    private double humidity;
    private double tempInDay;
    private double tempInNight;
    private String description;
    private long date;
    private int imagePath;

    public CityDailyWeather(double windSpeed, double humidity, double tempInDay, double tempInNight, String description, long date) {
        this.windSpeed = windSpeed;
        this.humidity = humidity;
        this.tempInDay = tempInDay;
        this.tempInNight = tempInNight;
        this.description = description;
        this.date = date;
        this.imagePath = WeatherImage.getImageRecourse(description,"day",tempInDay);

    }


    public double getWindSpeed() {
        return windSpeed;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getTempInDay() {
        return tempInDay;
    }

    public double getTempInNight() {
        return tempInNight;
    }

    public String getDescription() {
        return description;
    }

    public long getDate() {
        return date;
    }

    public int getImagePath() {
        return imagePath;
    }
}
