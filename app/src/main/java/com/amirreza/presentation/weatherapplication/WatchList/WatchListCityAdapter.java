package com.amirreza.presentation.weatherapplication.WatchList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.amirreza.domain.entity.WatchListWeather;
import com.amirreza.presentation.weatherapplication.MainActivityViewModel;
import com.amirreza.weatherapplication.R;


import java.util.ArrayList;

public class WatchListCityAdapter extends RecyclerView.Adapter<WatchListCityAdapter.cityWeather> {

    private ArrayList<WatchListWeather> cities;
    private FragmentActivity context;

    public WatchListCityAdapter(ArrayList<WatchListWeather> cities, FragmentActivity context ){
        this.cities = cities;
        this.context = context;
    }

    public void setCities(ArrayList<WatchListWeather> cities){
        this.cities.clear();
        notifyDataSetChanged();
        this.cities = cities;
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

    public void addWatchListWeather(WatchListWeather watchListWeather){

        if (isCityUnique(watchListWeather)){
            cities.add(0,watchListWeather);
            notifyItemChanged(0);
        }
    }

    public void deleteCityFromWatchList(WatchListWeather watchListWeather){
        for(int i=0;i<cities.size();++i){
            if(watchListWeather.getLon()==(cities.get(i).getLon()) && watchListWeather.getLat()==(cities.get(i).getLat())){
                cities.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    public boolean isCityUnique(WatchListWeather watchListWeather){
        for(int i=0;i<cities.size();++i){
            if(watchListWeather.getCityName().equals(cities.get(i).getCityName()))
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
        private MainActivityViewModel mainActivityViewModel;
        private MutableLiveData<WatchListWeather> watchListWeatherMutableLiveData;

        public cityWeather(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.watchlist_city_item_description);
            weatherImage = itemView.findViewById(R.id.watchlist_city_item_image);
            cityName = itemView.findViewById(R.id.watchlist_city_item_city_name);
            temperature = itemView.findViewById(R.id.watchlist_city_item_temperature);

            mainActivityViewModel = new ViewModelProvider(context).get(MainActivityViewModel.class);
        }

        public void bindData(WatchListWeather watchListWeather){
            cityName.setText(watchListWeather.getCityName());
            temperature.setText(watchListWeather.getTemperature());
            description.setText(watchListWeather.getDescription());
            weatherImage.setImageResource(Integer.parseInt(watchListWeather.getWeatherImagePath()));

            itemView.setOnClickListener(v -> {
                mainActivityViewModel.getIsWatchListItemSelect().setValue(true);
                mainActivityViewModel.getWatchListItemSelected().setValue(watchListWeather);
            });

            itemView.setOnLongClickListener(v -> {
                if(cities.size()>1) {
                    mainActivityViewModel.getDeleteWatchList().setValue(watchListWeather);
                    mainActivityViewModel.getNavigateToDialogDeleteWatchList().setValue(true);
                    return true;
                }
                return true;
            });
        }
    }
}
