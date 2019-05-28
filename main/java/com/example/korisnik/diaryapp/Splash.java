package com.example.korisnik.diaryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//load the password
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        password = settings.getString("password", "");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(password.equals("")) {
                    //if there is no password
                    Intent intent = new Intent(getApplicationContext(), CreatePassword.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    //if there is a password
                    Intent intent = new Intent(getApplicationContext(), EnterPassw.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }
}
