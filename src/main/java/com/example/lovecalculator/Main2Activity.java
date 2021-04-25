package com.example.lovecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {

            public void run() {
                Intent i = new Intent(Main2Activity.this, MainActivity.class);
                Main2Activity.this.startActivity(i);
                Main2Activity.this.finish();
            }
        },2000);
        }


    }

