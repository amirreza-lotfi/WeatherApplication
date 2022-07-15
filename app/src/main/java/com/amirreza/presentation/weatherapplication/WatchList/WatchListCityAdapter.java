package com.amirreza.presentation.weatherapplication.WatchList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.amirreza.domain.entity.SavedCityWeather;
import com.amirreza.presentation.weatherapplication.DialogFragmentOfWatchList.util.OnItemClickCallBackWatchList;
import com.amirreza.weatherapplication.R;


import java.util.ArrayList;

public class WatchListCityAdapter extends RecyclerView.Adapter<WatchListCityAdapter.cityWeather> {

    private ArrayList<SavedCityWeather> cities;
    private final OnItemClickCallBackWatchList onItemClickCallBack;

    public WatchListCityAdapter(ArrayList<SavedCityWeather> cities, OnItemClickCallBackWatchList onItemClickCallBack ){
        this.cities = cities;
        this.onItemClickCallBack = onItemClickCallBack;
    }

    public void setCities(ArrayList<SavedCityWeather> cities){
        this.cities.clear();
        notifyDataSetChanged();
    }


    @Override
    public cityWeather onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.watchlist_city_item,parent,false);
        return new cityWeather(view);
    }

    @Override
    public void onBindViewHolder(cityWeather holder, int position) {
        holder.bindData(cities.get(position));
    }

    public void addWatchListWeather(SavedCityWeather savedCityWeather){

        if (isCityUnique(savedCityWeather)){
            cities.add(0, savedCityWeather);
            notifyItemChanged(0);
        }
    }

    public void deleteCityFromWatchList(SavedCityWeather savedCityWeather){
        for(int i=0;i<cities.size();++i){
            if(savedCityWeather.getLon()==(cities.get(i).getLon()) && savedCityWeather.getLat()==(cities.get(i).getLat())){
                cities.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    public boolean isCityUnique(SavedCityWeather savedCityWeather){
        for(int i=0;i<cities.size();++i){
            if(savedCityWeather.getCityName().equals(cities.get(i).getCityName()))
                return false;
        }
        return true;
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class cityWeather extends  RecyclerView.ViewHolder {
        private final AppCompatImageView weatherImage;
        private final TextView description;
        private final TextView temperature;
        private final TextView cityName;

        public cityWeather(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.watchlist_city_item_description);
            weatherImage = itemView.findViewById(R.id.watchlist_city_item_image);
            cityName = itemView.findViewById(R.id.watchlist_city_item_city_name);
            temperature = itemView.findViewById(R.id.watchlist_city_item_temperature);
        }

        public void bindData(SavedCityWeather savedCityWeather){
            cityName.setText(savedCityWeather.getCityName());
            temperature.setText(savedCityWeather.getTemperature());
            description.setText(savedCityWeather.getDescription());
            weatherImage.setImageResource(Integer.parseInt(savedCityWeather.getWeatherImagePath()));

            itemView.setOnClickListener(view -> onItemClickCallBack.onClickWatchListItem(savedCityWeather));

            itemView.setOnLongClickListener(view -> {
                onItemClickCallBack.onLongClickWatchListItem(savedCityWeather);
                return true;
            });

        }
    }

}
