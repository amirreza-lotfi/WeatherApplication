package com.amirreza.presentation.weatherapplication.CityFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.amirreza.domain.entity.CityDailyWeather;
import com.amirreza.domain.entity.TimeProcess;
import com.amirreza.domain.entity.WeatherImage;
import com.amirreza.weatherapplication.R;
import com.amirreza.weatherapplication.databinding.DailyWeatherItemBinding;

import java.util.List;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder> {
    List<CityDailyWeather> dailyWeather;
    DailyWeatherItemBinding binding;

    public DailyWeatherAdapter(List<CityDailyWeather> dailyWeather){
        this.dailyWeather = dailyWeather;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = DailyWeatherItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        View root = binding.getRoot();
        return new ViewHolder(root);

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
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void bindData(CityDailyWeather daily){
            binding.tempValue.setText((daily.getTemperature().getDay()+"°/ "+daily.getTemperature().getDay()+"°"));
            binding.windValue.setText(daily.getWindSpeed()+" km/h");
            binding.humidityValue.setText(daily.getHumidity()+"%");
            binding.weatherDescriptionImage.setImageResource(
                WeatherImage.getImageRecourse(
                        daily.getWeatherDescription().get(0).getDescription(),"day",daily.getTemperature().getDay()
                )
            );
            binding.weatherDescription.setText((CharSequence) daily.getWeatherDescription());

            TimeProcess timeProcess = new TimeProcess(daily.getDt());
            binding.timeDaily.setText(timeProcess.getDateIn_MONTH_DAY_DAYOFWEEK_format());
        }
    }
}
