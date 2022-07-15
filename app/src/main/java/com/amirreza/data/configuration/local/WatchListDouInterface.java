package com.amirreza.data.configuration.local;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.amirreza.domain.entity.SavedCityWeather;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface WatchListDouInterface {
    @Insert(onConflict = REPLACE)
    long add(SavedCityWeather savedCityWeather);

    @Delete
    int delete(SavedCityWeather savedCityWeather);

    @Query("SELECT * From table_watch_lists order by createdTime desc")
    Single<List<SavedCityWeather>> getAllCities();

    @Query("SELECT count(*) From table_watch_lists")
    int getNumberOfCityInDatabase();

}
