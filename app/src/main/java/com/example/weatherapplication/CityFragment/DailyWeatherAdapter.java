package com.example.weatherapplication.CityFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapplication.R;
import com.example.weatherapplication.TimeProcess;
import com.example.weatherapplication.WeatherImage;

import java.util.ArrayList;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder> {
    ArrayList<CityDailyWeather> dailyWeather;

    public DailyWeatherAdapter(ArrayList<CityDailyWeather> dailyWeather){
        this.dailyWeather = dailyWeather;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_weather_item,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CityDailyWeather city = dailyWeather.get(position);
        holder.bindData(city);
    }

    @Override
    public int getItemCount() {
        return dailyWeather.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView time;
        AppCompatTextView weatherDescription;
        AppCompatTextView tempRangeInDay;
        AppCompatTextView windSpeed;
        AppCompatTextView humidity;
        AppCompatImageView weatherImage;

        public ViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time_daily);
            weatherDescription = itemView.findViewById(R.id.weather_description);
            tempRangeInDay = itemView.findViewById(R.id.temp_value);
            windSpeed = itemView.findViewById(R.id.wind_value);
            humidity = itemView.findViewById(R.id.humidity_value);
            weatherImage = itemView.findViewById(R.id.weather_description_image);
        }

        public void bindData(CityDailyWeather daily){
            tempRangeInDay.setText((int)daily.getTempInDay()+"°/ "+(int)daily.getTempInNight()+"°");
            windSpeed.setText(daily.getWindSpeed()+" km/h");
            humidity.setText(daily.getHumidity()+"%");
            weatherImage.setImageResource(WeatherImage.getImageRecourse(daily.getDescription(),"day",daily.getTempInDay()));
            weatherDescription.setText(daily.getDescription());

            TimeProcess timeProcess = new TimeProcess(daily.getDate());
            time.setText(timeProcess.getDateIn_MONTH_DAY_DAYOFWEEK_format());
        }

    }


}
