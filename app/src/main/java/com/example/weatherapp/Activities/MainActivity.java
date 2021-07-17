package com.example.weatherapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.weatherapp.DataRepository.DataViewModel;
import com.example.weatherapp.R;
import com.example.weatherapp.Room.Datafeed;
import com.example.weatherapp.Room.Room_Db;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static com.example.weatherapp.R.id.add_city;
import static com.example.weatherapp.R.id.city_adder;

public class MainActivity extends AppCompatActivity{

    ImageView city_add_button;
    TextInputEditText city_enter;
    ImageView city_view;
    TextView city_dis;
    TextView temp_dis;
    TextView humid_dis;
    TextView discrip_dis;
    ImageView icon_dis;
    TextView invalid;
    LinearLayout hidden;
    DataViewModel viewModel;

    String name;
    String temperature;
    String humidity;
    Bitmap bitmap;
    String descrip;
    Long time;
    String stime;

    //ROOM
    Room_Db room;
    static  Room_Db room1;
    List<Datafeed> datafeedList = new ArrayList<>();


    private String ApiKey = "a7161ee1e3bfd5c103b2cd576fc3aa15";
    private String cityname = "London";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(DataViewModel.class);
        room1 = Room.databaseBuilder(getApplicationContext(), Room_Db.class,"Weathererdatabase").allowMainThreadQueries().build();

        city_add_button = findViewById(add_city);
        city_enter = findViewById(city_adder);
        city_view = findViewById(R.id.setting);
        city_dis = findViewById(R.id.city_display);
        temp_dis = findViewById(R.id.temp_display);
        humid_dis = findViewById(R.id.humidity_display);
        icon_dis = findViewById(R.id.icon_display);
        hidden =  findViewById(R.id.hidden);
        discrip_dis = findViewById(R.id.description);
        invalid = findViewById(R.id.invalid);

        //initialise database
        room = Room_Db.getInstance(this);
        //store database value in the data list
        datafeedList = room.data_dao().getAll();


        city_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, citylist.class);
                startActivity(intent);
            }
        });

        city_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cityname = city_enter.getText().toString().toUpperCase();

                WeatherTask weatherTask = new WeatherTask();
                String apiurl = "https://api.openweathermap.org/data/2.5/weather?q=" + cityname + "&units=metric&appid=" + ApiKey;
                String gett = null;
                JSONObject jsonObject = null;
                JSONArray jsonArray = null;
                try {
                    gett = weatherTask.execute(apiurl).get();


                    jsonObject = new JSONObject(gett);
                    jsonArray = jsonObject.getJSONArray("weather");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    temperature = jsonObject.getJSONObject("main").getString("temp");
                    humidity = jsonObject.getJSONObject("main").getString("humidity");
                    time = jsonObject.getLong("dt");
                    stime = new SimpleDateFormat("dd-M-yyyy | hh:mm:ss", Locale.ENGLISH)
                            .format(new Date(time*1000));
                    descrip = jsonObject1.getString("description");

                    temp_dis.setText(temperature + " \u2103");
                    humid_dis.setText(" " + humidity);
                    city_dis.setText(cityname);
                    discrip_dis.setText(descrip);

                    hidden.setVisibility(View.VISIBLE);
                    name = jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

                String icon_url = "https://openweathermap.org/img/wn/" + name + "@2x.png";

                WeatherIcon weatherIcon = new WeatherIcon();
                try {
                    bitmap = weatherIcon.execute(icon_url).get();
                    icon_dis.setImageBitmap(bitmap);

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Datafeed datafeed = new Datafeed();
                if(!cityname.equals(""))
                {
                    datafeed.setCity(cityname);
                    datafeed.setHumid(humidity);
                    datafeed.setTemp(temperature + " \u2103");
                    datafeed.setDatetime(stime);
                    datafeed.setDescription(descrip);
                }

                //insert into database
                viewModel.insert(datafeed);

                //notify when data is changed..
                datafeedList.clear();
                datafeedList.addAll(room.data_dao().getAll());

            }

        });

       // adapter = new RecylerAdapter(datalist,this);

    }

    public class WeatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            StringBuffer json = null;

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                
                json = new StringBuffer(5000);
                String temp = "";
                while ((temp = reader.readLine()) != null) {
                    json.append(temp).append("\n");
                }
                reader.close();
                urlConnection.disconnect();

            } catch (Exception e) {
                e.getMessage();
            }

            return json.toString();
        }
    }

    public class WeatherIcon extends AsyncTask<String , Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {

            Bitmap bitmap = null;

            URL url;
            HttpURLConnection httpURLConnection;
            InputStream inputStream;

            try {
                url = new URL(strings[0]);

                httpURLConnection = (HttpURLConnection) url.openConnection();

                inputStream = httpURLConnection.getInputStream();

                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch (IOException e) {
                    e.getMessage();
            }
            return bitmap;
        }

    }

}