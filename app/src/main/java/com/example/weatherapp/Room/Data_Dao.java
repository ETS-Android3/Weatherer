package com.example.weatherapp.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface Data_Dao {

    //insert query
    @Insert(onConflict = REPLACE)
    void insert(Datafeed datafeed);

    //delete query
    @Delete
    void delete(Datafeed datafeed);

    //delete all
    @Query("DELETE FROM weather_data")
    void reset();

    //get all data
    @Query("SELECT * FROM weather_data")
    List<Datafeed>getAll();
}
