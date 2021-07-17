package com.example.weatherapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.weatherapp.R;

public class Splash_activity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME = 1500;

    Animation top;
    ImageView sunny;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_activity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN_TIME);

        sunny = findViewById(R.id.sun);

        top = AnimationUtils.loadAnimation(this,R.anim.top_anim);

        sunny.setAnimation(top);

    }
}