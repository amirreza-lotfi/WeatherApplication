package com.example.weatherapplication;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.weatherapplication.WatchList.WatchListDouInterface;
import com.example.weatherapplication.WatchList.WatchListWeather;

@Database(version = 1,exportSchema = false,entities = {WatchListWeather.class})
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
