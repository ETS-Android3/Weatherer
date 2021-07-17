package com.example.weatherapp.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//add database instance
@Database(entities = {Datafeed.class},version = 1,exportSchema = false)
public abstract class Room_Db extends RoomDatabase {

    //create database instance
    private static Room_Db room_db;

    //database name
    private static String DATABASE_NAME = "Weathererdatabase";

    public synchronized static Room_Db getInstance(Context context){

        //condn check
        if(room_db == null){

            //initialize database
            room_db = Room.databaseBuilder(context.getApplicationContext(),
                    Room_Db.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return room_db;
    }


    //create DAO
    public abstract Data_Dao data_dao();
}
