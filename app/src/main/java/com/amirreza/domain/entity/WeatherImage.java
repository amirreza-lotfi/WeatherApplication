package com.amirreza.domain.entity;

import com.amirreza.weatherapplication.R;

public class WeatherImage {
    public static int getImageRecourse(String description, String time, double temp){
        description = description.toLowerCase();
        //thunderstorm
        if (description.equals("thunderstorm with light rain")){
            if(time.equals("night"))
                return R.drawable.ic_thunderstorm_no_rain_night;
            else
                return R.drawable.ic_thunderstorm_no_rain;
        }
        else if (description.equals("thunderstorm with rain")){
            if(time.equals("night"))
                return R.drawable.ic_thunderstorm_rain_night;
            else
                return R.drawable.ic_thunderstorm_rain;
        }
        else if (description.equals("thunderstorm with heavy rain")){
            if(time.equals("night"))
                return R.drawable.ic_thunderstorm_rain_night;
            else
                return R.drawable.ic_thunderstorm_rain;
        }
        else if (description.equals("light thunderstorm")){
            if(time.equals("night"))
                return R.drawable.ic_thunderstorm_no_rain_night;
            else
                return R.drawable.ic_thunderstorm_no_rain;
        }
        else if (description.equals("thunderstorm")){
            if(time.equals("night"))
                return R.drawable.ic_thunderstorm_no_rain_night;
            else
                return R.drawable.ic_thunderstorm_no_rain;
        }
        else if (description.equals("heavy thunderstorm")){
            if(time.equals("night"))
                return R.drawable.ic_thunderstorm_no_rain_night;
            else
                return R.drawable.ic_thunderstorm_no_rain;
        }
        else if (description.equals("ragged thunderstorm")){
            if(time.equals("night"))
                return R.drawable.ic_thunderstorm_no_rain_night;
            else
                return R.drawable.ic_thunderstorm_no_rain;
        }
        else if (description.equals("thunderstorm with light drizzle")){
            if(time.equals("night"))
                return R.drawable.ic_thunderstorm_no_rain_night;
            else
                return R.drawable.ic_thunderstorm_no_rain;
        }
        else if (description.equals("thunderstorm with drizzle")){
            if(time.equals("night"))
                return R.drawable.ic_thunderstorm_no_rain_night;
            else
                return R.drawable.ic_thunderstorm_no_rain;
        }
        else if (description.equals("thunderstorm with heavy drizzle")){
            if(time.equals("night"))
                return R.drawable.ic_thunderstorm_rain_night;
            else
                return R.drawable.ic_thunderstorm_rain;
        }

        //drizzle
        else if (description.equals("light intensity drizzle")){
            if(time.equals("night"))
                return R.drawable.ic_drizzle_night;
            else
                return R.drawable.ic_drizzle;
        }
        else if (description.equals("drizzle")){
            if(time.equals("night"))
                return R.drawable.ic_drizzle_night;
            else
                return R.drawable.ic_drizzle;
        }
        else if (description.equals("heavy intensity drizzle")){
            if(time.equals("night"))
                return R.drawable.ic_rain_drizzle_night;
            else
                return R.drawable.ic_rain_drizzle;
        }
        else if (description.equals("light intensity drizzle rain")){
            if(time.equals("night"))
                return R.drawable.ic_drizzle_night;
            else
                return R.drawable.ic_drizzle;
        }
        else if (description.equals("drizzle rain")){
            if(time.equals("night"))
                return R.drawable.ic_rain_drizzle_night;
            else
                return R.drawable.ic_rain_drizzle;
        }
        else if (description.equals("heavy intensity drizzle rain")){
            if(time.equals("night"))
                return R.drawable.ic_rain_drizzle_night;
            else
                return R.drawable.ic_rain_drizzle;
        }
        else if (description.equals("shower rain and drizzle")){
            if(time.equals("night"))
                return R.drawable.ic_rain_drizzle_night;
            else
                return R.drawable.ic_rain_drizzle;
        }
        else if (description.equals("heavy shower rain and drizzle")){
            if(time.equals("night"))
                return R.drawable.ic_rain_drizzle_night;
            else
                return R.drawable.ic_rain_drizzle;
        }
        else if (description.equals("shower drizzle")){
            if(time.equals("night"))
                return R.drawable.ic_rain_drizzle_night;
            else
                return R.drawable.ic_rain_drizzle;
        }

        //rain
        else if (description.equals("light rain")){
            if(time.equals("night"))
                return R.drawable.ic_drizzle_night;
            else
                return R.drawable.ic_drizzle;
        }
        else if (description.equals("moderate rain")){
            if(time.equals("night"))
                return R.drawable.ic_heavy_rain_night;
            else
                return R.drawable.ic_heavy_rain;
        }
        else if (description.equals("heavy intensity rain")){
            if(time.equals("night"))
                return R.drawable.ic_heavy_rain_night;
            else
                return R.drawable.ic_heavy_rain;
        }
        else if (description.equals("very heavy rain")){
            if(time.equals("night"))
                return R.drawable.ic_heavy_rain_night;
            else
                return R.drawable.ic_heavy_rain;
        }
        else if (description.equals("extreme rain")){
            if(time.equals("night"))
                return R.drawable.ic_heavy_rain_night;
            else
                return R.drawable.ic_heavy_rain;
        }
        else if (description.equals("freezing rain")){
            if(time.equals("night"))
                return R.drawable.ic_freezing_rain;
        }
        else if (description.equals("light intensity shower rain")){
            if(time.equals("night"))
                return R.drawable.ic_heavy_rain_night;
            else
                return R.drawable.ic_heavy_rain;
        }
        else if (description.equals("shower rain")){
            if(time.equals("night"))
                return R.drawable.ic_heavy_rain_night;
            else
                return R.drawable.ic_heavy_rain;
        }
        else if (description.equals("heavy intensity shower rain")){
            if(time.equals("night"))
                return R.drawable.ic_heavy_rain_night;
            else
                return R.drawable.ic_heavy_rain;
        }
        else if (description.equals("ragged shower rain")){
            if(time.equals("night"))
                return R.drawable.ic_heavy_rain_night;
            else
                return R.drawable.ic_heavy_rain;
        }

        //snow
        else if(description.equals("light snow") || description.equals("snow") ||
                description.equals("heavy snow") || description.equals("shower snow") ||
                description.equals("light shower snow") ||
                description.equals("heavy shower snow")){
            if(time.equals("night"))
                return R.drawable.ic_snow_night;
            else
                return R.drawable.ic_snow;
        }

        else if(description.equals("sleet") || description.equals("light shower sleet") ||
                description.equals("shower sleet") || description.equals("light rain and snow") ||
                description.equals("rain and snow")){
            if(time.equals("night"))
                return R.drawable.ic_rain_snow_night;
            else
                return R.drawable.ic_rain_snow;
        }

        //clear sky
        else if (description.equals("clear sky")){
            if(time.equals("night")){
                return R.drawable.ic_clear_sky_night;
            }
            else if(time.equals("day") && temp > 45){
                return R.drawable.ic_very_hot;
            }
            else
                return R.drawable.ic_clear_sky;
        }

        //clouds
        else if (description.equals("few clouds")){
            if(time.equals("night"))
                return R.drawable.ic_scattered_clouds_night;
            else
                return R.drawable.ic_few_clouds;
        }
        else if (description.equals("scattered clouds")){
            if(time.equals("night"))
                return R.drawable.ic_scattered_clouds_night;
            else
                return R.drawable.ic_scattered_clouds;
        }
        else if (description.equals("broken clouds")){
            if(time.equals("night"))
                return R.drawable.ic_two_clouds_night;
            else
                return R.drawable.ic_two_clouds;
        }
        else if (description.equals("overcast clouds")){
            if(time.equals("night"))
                return R.drawable.ic_two_clouds_night;
            else
                return R.drawable.ic_two_clouds;
        }


        //atmosphere
        else if(description.equals("tornado") || description.equals("squalls")){
            if(time.equals("night"))
                return R.drawable.ic_windy;
            else
                return R.drawable.ic_windy_night;
        }
        else if(description.equals("fog") || description.equals("haze")){
            if(time.equals("night"))
                return R.drawable.ic_fog_night;
            else
                return R.drawable.ic_fog;
        }
        else if(description.equals("volcanic ash")){
            return R.drawable.ic_volcanic_ash;
        }
        else if(description.equals("dust") || description.equals("sand") ||
                description.equals("sand/ dust whirls") || description.equals("smoke")){
            return R.drawable.ic_dust;
        }
        return R.drawable.ic_not_founded;
    }
}
