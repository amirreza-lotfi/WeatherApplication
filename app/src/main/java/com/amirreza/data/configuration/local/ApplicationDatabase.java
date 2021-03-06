package com.amirreza.data.configuration.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.amirreza.domain.entity.SavedCityWeather;

@Database(version = 1,exportSchema = false,entities = {SavedCityWeather.class})
public abstract class ApplicationDatabase extends RoomDatabase {
    private static ApplicationDatabase applicationDatabase;

    public static ApplicationDatabase getInstance(Context context){
        if(applicationDatabase ==null){
            applicationDatabase = Room.databaseBuilder(context.getApplicationContext(), ApplicationDatabase.class,"Application_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return applicationDatabase;
    }
    public abstract WatchListDouInterface getWatchListDao();
}
