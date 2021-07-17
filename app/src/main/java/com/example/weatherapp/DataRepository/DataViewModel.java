package com.example.weatherapp.DataRepository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.weatherapp.DataRepository.DataRepo;
import com.example.weatherapp.Room.Datafeed;

public class DataViewModel  extends AndroidViewModel {

    private DataRepo dataRepo;


    public DataViewModel(@NonNull Application application) {
        super(application);
        dataRepo = new DataRepo(application);
    }

    public void insert(Datafeed datafeed)
    {
        dataRepo.insert(datafeed);
    }

    public void  delete(Datafeed datafeed)
    {
        dataRepo.delete(datafeed);
    }

    public void reset(){
        dataRepo.deleteAll();
    }

}
