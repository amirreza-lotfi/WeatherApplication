package com.amirreza.presentation.weatherapplication;

import android.content.Context;

import com.amirreza.domain.entity.CityInformation;
import com.amirreza.weatherapplication.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class AllCityInTheWorld {

    private LinkedList<String> cityName;

    public AllCityInTheWorld(Context context){
        try {
            cityName = new LinkedList<>();

            InputStream cityInput = context.getResources().openRawResource(R.raw.all);
            BufferedReader cityReader = new BufferedReader(new InputStreamReader(cityInput));
            String cityLine;


            while ((cityLine = cityReader.readLine()) != null) {
                cityName.add(cityLine);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public LinkedList<String> getAllCitiesInTheWorld(){
        return this.cityName;
    }

    public CityInformation getCityCoordinators(String name){
        for(int i=0;i<cityName.size();++i){
            String city = cityName.get(i);
            String[] cityData = cityName.get(i).split("!");
            if(cityData[0].equals(name) && cityData[0].length() == name.length()){
                return new CityInformation(cityData[2],cityData[3]);
            }
        }
        return null;
    }

}