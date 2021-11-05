package com.example.weatherapplication.CityFragment;

import com.example.weatherapplication.WeatherImage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CityWeatherDataInOneCall {

    private double temperature;
    private double feelLike;
    private double pressure;
    private double humidity;
    private double windSpeed;
    private long sunset;
    private long sunrise;
    private long time;
    private String description;
    private String timeDescription;
    private String country;
    private int picturePath;

    private int dayTemp;
    private int nightTemp;

    private ArrayList<CityHourlyWeather> cityHourlyWeathers;
    private ArrayList<CityDailyWeather> cityDailyWeathers;


    CityWeatherDataInOneCall(){}
    public CityWeatherDataInOneCall(String jsonString){
        try {
            cityHourlyWeathers = new ArrayList<>(50);
            cityDailyWeathers = new ArrayList<>(10);

            JSONObject data = new JSONObject(jsonString);



            //set main data
            JSONObject current = data.getJSONObject("current");
            this.temperature = current.getDouble("temp");
            this.feelLike = current.getDouble("feels_like");
            this.pressure = current.getDouble("pressure");
            this.humidity = current.getDouble("humidity");
            this.windSpeed = current.getDouble("wind_speed");
            this.sunrise = current.getLong("sunrise");
            this.sunset = current.getLong("sunset");
            this.time = current.getLong("dt");


            //set description
            JSONArray weather = current.getJSONArray("weather");
            this.description = weather.getJSONObject(0).get("description").toString();


            //set city information
            this.country = data.getString("timezone");

            setTimeDescription();

            picturePath = WeatherImage.getImageRecourse(description,timeDescription,temperature);

            //temp range in day
            JSONObject tempDaily = data.getJSONArray("daily").getJSONObject(0).getJSONObject("temp");
            dayTemp = (int)tempDaily.getDouble("max");
            nightTemp = (int)tempDaily.getDouble("min");

            //hourly data
            JSONArray hourly =  data.getJSONArray("hourly");
            for(int i=0;i<hourly.length();++i){
                JSONObject hourlyJson = hourly.getJSONObject(i);

                long dataReceivingTime = hourlyJson.getLong("dt");
                double temperature = hourlyJson.getLong("temp");
                String description = hourlyJson.getJSONArray("weather").getJSONObject(0).getString("description");

                CityHourlyWeather cityHourlyWeather = new CityHourlyWeather(dataReceivingTime,temperature,description,sunrise,sunset);
                cityHourlyWeathers.add(cityHourlyWeather);

            }

            //daily data
            JSONArray daily =  data.getJSONArray("daily");
            for(int i=0;i<daily.length();++i){
                JSONObject dailyJson = daily.getJSONObject(i);
                JSONObject temp = dailyJson.getJSONObject("temp");

                double windSpeed = dailyJson.getDouble("wind_speed");
                double humidity = dailyJson.getDouble("humidity");
                double tempInDay = temp.getDouble("max");
                double tempInNight = temp.getDouble("min");
                String description = dailyJson.getJSONArray("weather").getJSONObject(0).getString("description");
                long date = dailyJson.getLong("dt");
                CityDailyWeather cityDailyWeather = new CityDailyWeather(windSpeed,humidity,tempInDay,tempInNight,description,date);

                cityDailyWeathers.add(cityDailyWeather);

            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public int getPicturePath(){
        return this.picturePath;
    }

    public int getTemperature(){
        return (int)temperature;
    }

    public int getFeelLike(){
        return (int)feelLike;
    }

    public String getDescription(){
        return description;
    }

    public void setTimeDescription(){
        if (time >= sunrise && time <=sunset)
            timeDescription = "day";
        else
            timeDescription = "night";
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getHumidity(){
        return this.humidity;
    }

    public double getPressure(){
        return this.pressure;
    }

    public long getTime() {
        return time;
    }

    public int getDayTemp() {
        return dayTemp;
    }

    public int getNightTemp() {
        return nightTemp;
    }

    public ArrayList<CityHourlyWeather> getCityHourlyWeathers() {
        return cityHourlyWeathers;
    }

    public ArrayList<CityDailyWeather> getCityDailyWeathers() {
        return cityDailyWeathers;
    }

    public long getSunset() {
        return sunset;
    }

    public long getSunrise() {
        return sunrise;
    }

    public String getTimeDescription() {
        return timeDescription;
    }
}
