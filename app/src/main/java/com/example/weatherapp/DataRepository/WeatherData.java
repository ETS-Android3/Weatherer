package com.example.weatherapp.DataRepository;

import android.graphics.Bitmap;

public class WeatherData {

    String city_card;
    String temp_card;
    String humid_card;
    Bitmap icon;

    public WeatherData(String city_card, String temp_card, String humid_card, Bitmap icon)
    {
        this.city_card = city_card;
        this.temp_card = temp_card;
        this.humid_card = humid_card;
        this.icon = icon;
    }

    public String getCity_card() {
        return city_card;
    }

    public String getTemp_card() {
        return temp_card;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public String getHumid_card() {
        return humid_card;
    }

    public void setCity_card(String city_card) {
        this.city_card = city_card;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public void setHumid_card(String humid_card) {
        this.humid_card = humid_card;
    }

    public void setTemp_card(String temp_card) {
        this.temp_card = temp_card;
    }
}
