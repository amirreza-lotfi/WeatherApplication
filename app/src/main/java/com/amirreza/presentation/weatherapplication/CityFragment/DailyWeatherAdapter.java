package com.amirreza.presentation.weatherapplication.CityFragment;

import static com.amirreza.common.GetImageRecourseKt.getImageRecourse;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;


import com.amirreza.domain.entity.CityAllWeatherDataEntity.DailyWeather;
import com.amirreza.domain.entity.TimeProcess;
import com.amirreza.weatherapplication.databinding.DailyWeatherItemBinding;

import java.util.List;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder> {
    List<DailyWeather> dailyWeather;
    DailyWeatherItemBinding binding;

    public DailyWeatherAdapter(List<DailyWeather> dailyWeather){
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
        DailyWeather city = dailyWeather.get(position);
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

        public void bindData(DailyWeather daily){
            binding.tempValue.setText((daily.getTemp().getDay()+"°/ "+daily.getTemp().getDay()+"°"));
            binding.windValue.setText(daily.getWindSpeed()+" km/h");
            binding.humidityValue.setText(daily.getHumidity()+"%");
            binding.weatherDescriptionImage.setImageResource(
                getImageRecourse(
                        daily.getWeather().get(0).getDescription(),"day",daily.getTemp().getDay()
                )
            );
            binding.weatherDescription.setText(daily.getWeather().get(0).getDescription());

            TimeProcess timeProcess = new TimeProcess(daily.getDt());
            binding.timeDaily.setText(timeProcess.getDateIn_MONTH_DAY_DAYOFWEEK_format());
        }
    }
}
