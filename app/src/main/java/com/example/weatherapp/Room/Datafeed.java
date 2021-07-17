package com.example.weatherapp.Room;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//table name..
@Entity(tableName = "weather_data")
public class Datafeed implements Serializable {

    //create the col id
    @PrimaryKey(autoGenerate = true)
    private int id;

    //create the city col
    @ColumnInfo(name = "city")
    private String city;

    //create the temp col
    @ColumnInfo(name = "temp")
    private String temp;

    //create the humidity col
    @ColumnInfo(name = "humid")
    private String humid;

    @ColumnInfo(name = "datetime")
    private String datetime;

    @ColumnInfo(name = "description")
    private String description;

    // getter setter

    public String getCity() {
        return city;
    }

    public String getTemp() {
        return temp;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getHumid() {
        return humid;
    }


    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHumid(String humid) {
        this.humid = humid;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
