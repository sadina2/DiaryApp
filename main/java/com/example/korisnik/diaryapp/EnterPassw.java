package com.example.korisnik.diaryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterPassw extends AppCompatActivity {


    EditText editText;
    Button button, button2;

    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_passw);

        //load the password
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        password = settings.getString("password", "");


                editText = (EditText) findViewById(R.id.editText);
                button = (Button) findViewById(R.id.button);
                button2 = (Button) findViewById(R.id.button2);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String text = editText.getText().toString();
                        if (text.equals(password)) {
                            //enter the app
                            Intent intent = new Intent(getApplicationContext(), DiaryListActivity.class); //MAinActivity
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(EnterPassw.this, "Wrong pasww", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), CreatePassword.class); //Splash
                        startActivity(intent);
                        finish();

                    }
                });

    }
}
