package com.example.weatherapplication.CityFragment;


import com.example.weatherapplication.WeatherImage;

public class CityHourlyWeather {
    private long dataReceivingTime;
    private double temperature;
    private String description;
    private int imagePath;

    CityHourlyWeather(long dataReceivingTime, double temperature,String description,long sunrise, long sunset){
        this.dataReceivingTime = dataReceivingTime;
        this.temperature = temperature;
        this.description = description;
        setImage(sunrise,sunset);
    }


    public void setImage(long sunrise, long sunset){
        if (dataReceivingTime >= sunrise && dataReceivingTime<=sunset)
            this.imagePath = WeatherImage.getImageRecourse(description,"day",temperature);
        else
            this.imagePath = WeatherImage.getImageRecourse(description,"night",temperature);
    }

    public long getDataReceivingTime() {
        return dataReceivingTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getImagePath() {
        return imagePath;
    }

}
