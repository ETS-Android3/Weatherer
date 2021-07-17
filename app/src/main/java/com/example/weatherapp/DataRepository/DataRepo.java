package com.example.weatherapp.DataRepository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.weatherapp.Room.Data_Dao;
import com.example.weatherapp.Room.Datafeed;
import com.example.weatherapp.Room.Room_Db;

public class DataRepo {

    private Data_Dao data_dao;

    public DataRepo(Application application){
        Room_Db room_db = Room_Db.getInstance(application);
        data_dao = room_db.data_dao();
    }

    public void insert(Datafeed datafeed){
        new insertsync(data_dao).execute(datafeed);
    }

    public void delete(Datafeed datafeed){
        new deletesync(data_dao).execute(datafeed);
    }

    public void deleteAll(){
        new deleteallsync(data_dao).execute();
    }

    public static class insertsync extends AsyncTask<Datafeed,Void ,Void>
    {
        Data_Dao data_dao;

        private insertsync(Data_Dao data_dao){
            this.data_dao = data_dao;
        }

        @Override
        protected Void doInBackground(Datafeed... datafeeds) {

            data_dao.insert(datafeeds[0]);
            return null;
        }
    }


    public static class deletesync extends AsyncTask<Datafeed,Void ,Void>
    {

        Data_Dao data_dao;

        private deletesync(Data_Dao data_dao){
            this.data_dao = data_dao;
        }

        @Override
        protected Void doInBackground(Datafeed... datafeeds) {

            data_dao.delete(datafeeds[0]);
            return null;
        }
    }


    public static class deleteallsync extends AsyncTask<Void,Void ,Void>
    {

        Data_Dao data_dao;

        private deleteallsync(Data_Dao data_dao)
        {
            this.data_dao = data_dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            data_dao.reset();
            return null;
        }
    }
}
