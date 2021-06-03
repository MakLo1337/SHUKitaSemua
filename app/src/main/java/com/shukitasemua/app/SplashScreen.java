package com.shukitasemua.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        Handler splash = new Handler();
        splash.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent (getApplicationContext(), login.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }
}