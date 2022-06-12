package com.amirreza.data.configuration.local;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.amirreza.domain.entity.WatchListWeather;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface WatchListDouInterface {
    @Insert(onConflict = REPLACE)
    long add(WatchListWeather watchListWeather);

    @Delete
    int delete(WatchListWeather watchListWeather);

    @Query("SELECT * From table_watch_lists order by createdTime desc")
    Single<List<WatchListWeather>> getAllCities();

    @Query("SELECT count(*) From table_watch_lists")
    int getNumberOfCityInDatabase();

}
