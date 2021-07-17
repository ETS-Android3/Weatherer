package com.example.weatherapp.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.Room.Datafeed;
import com.example.weatherapp.Room.Room_Db;
import com.example.weatherapp.DataRepository.WeatherData;

import java.util.ArrayList;
import java.util.List;

public class RecylerAdapter  extends RecyclerView.Adapter<RecylerAdapter.ViewHolde_rec> {

    private ArrayList<WeatherData> data;
    private Context context;
    private List<Datafeed> datafeedlist;
    Room_Db room_db;

    /*public RecylerAdapter(ArrayList<WeatherData>data,Context context)
    {
        this.context = context;
        this.data = data;
    }*/

    public RecylerAdapter(List<Datafeed>datafeedlist,Context context)
    {
        this.context = context;
        this.datafeedlist = datafeedlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolde_rec onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recycler_view,parent,false);
        return new ViewHolde_rec(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolde_rec holder, int position) {

        Datafeed datafeed_new = datafeedlist.get(position);

        //initialize database
        room_db = Room_Db.getInstance(context);

        holder.temp_card.setText(datafeed_new.getTemp());
        holder.city_name_card.setText(datafeed_new.getCity());
        holder.humid_card.setText(" " + datafeed_new.getHumid());
        holder.date_time.setText(datafeed_new.getDatetime());
        holder.des_card.setText(datafeed_new.getDescription());
        
    }

    @Override
    public int getItemCount() {
        return datafeedlist.size();
    }
    class ViewHolde_rec extends RecyclerView.ViewHolder{

        TextView humid_card;
        TextView city_name_card;
        TextView temp_card;
        TextView date_time;
        TextView des_card;

        public ViewHolde_rec(@NonNull View itemView) {
            super(itemView);

            humid_card = itemView.findViewById(R.id.humidity_display_card);
            city_name_card = itemView.findViewById(R.id.city_display_card);
            temp_card = itemView.findViewById(R.id.temp_display_card);
            date_time = itemView.findViewById(R.id.date_time_display);
            des_card = itemView.findViewById(R.id.description_card);

        }
    }
}
