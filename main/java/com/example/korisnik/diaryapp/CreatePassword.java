package com.example.korisnik.diaryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class CreatePassword extends AppCompatActivity {

    EditText editt1, editt2;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_passw);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
        editt1=(EditText) findViewById(R.id.editText1);
        editt2=(EditText) findViewById(R.id.editText2);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String text1 = editt1.getText().toString();
                String text2 = editt2.getText().toString();

                        if (text1.equals("") || text2.equals("")) {

                            Toast.makeText(CreatePassword.this, "Niste unijeli šifru", Toast.LENGTH_LONG).show();
                        } else {
                            if (text1.equals(text2)) {
                                //save the passw
                                SharedPreferences settings = getSharedPreferences("PREFS", 0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putString("password", text1);
                                editor.apply();


                                Intent intent = new Intent(getApplicationContext(), DiaryListActivity.class);
                                startActivity(intent);
                                finish();
                            } else {

                                Toast.makeText(CreatePassword.this, "Šifre se ne podudaraju", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }},2000);
    }
}
