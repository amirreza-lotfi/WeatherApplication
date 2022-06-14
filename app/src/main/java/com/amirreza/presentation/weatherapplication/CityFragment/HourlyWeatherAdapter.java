package com.amirreza.presentation.weatherapplication.CityFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.amirreza.domain.entity.OneCallWeatherEntitys.CityHourlyWeather;
import com.amirreza.domain.entity.TimeProcess;
import com.amirreza.weatherapplication.R;
import com.amirreza.weatherapplication.databinding.HourlyCityStateItemBinding;

import java.util.ArrayList;
import java.util.List;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder> {
    private List<CityHourlyWeather> cityHourlyWeathers;
    HourlyCityStateItemBinding binding;

    public HourlyWeatherAdapter(List<CityHourlyWeather> data){
        cityHourlyWeathers = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = HourlyCityStateItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        View view = binding.getRoot();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CityHourlyWeather cityHourlyWeather = cityHourlyWeathers.get(position);
        holder.bindData(cityHourlyWeather.getRequestTime(),cityHourlyWeather.getImagePath(),cityHourlyWeather.getTemp());
    }

    @Override
    public int getItemCount() {
        return cityHourlyWeathers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private AppCompatTextView time;
        private AppCompatImageView imageWeather;
        private AppCompatTextView temperature;

        public ViewHolder(View itemView) {
            super(itemView);
            this.time = binding.hourlyStateItemTime;
            this.imageWeather = binding.hourlyStateItemImage;
            this.temperature = binding.hourlyStateItemTemp;
        }

        public void bindData(long time, int imagePath, double temperature){
            TimeProcess timeProcess = new TimeProcess(time);
            String hourString;
            String minuteString;

            if(timeProcess.getHour() == 0)
                hourString = "00";
            else{
                hourString = timeProcess.getHour()+"";
            }

            if(timeProcess.getMinute() == 0)
                minuteString = "00";
            else{
                minuteString = timeProcess.getMinute()+"";
            }


            this.time.setText(hourString+":"+minuteString);
            this.imageWeather.setImageResource(imagePath);
            this.temperature.setText((int)temperature+"°");
        }
    }


}
