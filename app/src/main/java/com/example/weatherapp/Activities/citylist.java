package com.example.weatherapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.weatherapp.DataRepository.DataViewModel;
import com.example.weatherapp.R;
import com.example.weatherapp.Room.Datafeed;

import java.util.ArrayList;
import java.util.List;

public class citylist extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView reset_bu;
    List<Datafeed> data_get = new ArrayList<>();
    RecylerAdapter adapter;
    DataViewModel dataViewModel;
    ImageView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citylist);

        recyclerView = findViewById(R.id.city_list);
        reset_bu = findViewById(R.id.reset);
        info = findViewById(R.id.info_bu);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new ItemTouchHelper(itemtouchhelpercalback).attachToRecyclerView(recyclerView);
        getData();

        dataViewModel = new ViewModelProvider(this).get(DataViewModel.class);
        reset_bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialoginfo();
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infodialog();
            }
        });
    }

    private void getData(){
        class GetData extends AsyncTask<Void,Void,List<Datafeed>>{

            @Override
            protected List<Datafeed> doInBackground(Void... voids) {
                data_get = MainActivity.room1.data_dao().getAll();
                return data_get;
            }

            @Override
            protected void onPostExecute(List<Datafeed> datafeeds) {

                adapter = new RecylerAdapter(datafeeds,citylist.this);
                recyclerView.setAdapter(adapter);
                super.onPostExecute(datafeeds);
            }
        }
        GetData getDatatask = new GetData();
        getDatatask.execute();
    }


    ItemTouchHelper.SimpleCallback itemtouchhelpercalback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            Datafeed d = data_get.get(viewHolder.getAdapterPosition());
            dataViewModel.delete(d);
            int pos = viewHolder.getAdapterPosition();
            Toast.makeText(citylist.this, d.getCity() + " (" + d.getDatetime() + ")" + " Deleted", Toast.LENGTH_SHORT).show();
        }
    };

    public void Dialoginfo()
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.reset_dialog);
        dialog.setTitle("RESET DATABASE");

        Button bu = dialog.findViewById(R.id.reset_data_dia);

        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                dataViewModel.reset();
                adapter.notifyDataSetChanged();

                Toast.makeText(citylist.this," RESET Successful", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(600,600);
    }

    public void infodialog(){

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.info_dialog);
        dialog.setTitle("INFO");
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(600,600);

    }
}