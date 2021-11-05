package com.example.weatherapplication.CityFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.R;
import com.example.weatherapplication.TimeProcess;

import java.util.ArrayList;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder> {
    private ArrayList<CityHourlyWeather> cityHourlyWeathers;

    public HourlyWeatherAdapter(ArrayList<CityHourlyWeather> data){
        cityHourlyWeathers = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_city_state_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CityHourlyWeather cityHourlyWeather = cityHourlyWeathers.get(position);
        holder.bindData(cityHourlyWeather.getDataReceivingTime(),
                        cityHourlyWeather.getImagePath(),
                        cityHourlyWeather.getTemperature());
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
            this.time = itemView.findViewById(R.id.hourly_state_item_time);
            this.imageWeather = itemView.findViewById(R.id.hourly_state_item_image);
            this.temperature = itemView.findViewById(R.id.hourly_state_item_temp);
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
