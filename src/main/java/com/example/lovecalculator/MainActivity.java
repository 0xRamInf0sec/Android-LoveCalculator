package com.example.lovecalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText Name1,Name2;
    Button button,exit,clear;
    TextView textView,result;
    final String LOVE="love";
    int firstSum;
    int secondSum;
    int loveSum;
    int output;
    int totalSum;
    AlertDialog.Builder builder;
    AlertDialog alert;
    File imagePath;
    private static final int STORAGE_PERMISSION_CODE = 101;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name1=findViewById(R.id.Name1);
        Name2=findViewById(R.id.Name2);
        button=findViewById(R.id.button);
        clear=findViewById(R.id.clear);

        result=findViewById(R.id.result);
        textView=findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String firstname=Name1.getText().toString().toLowerCase();
                final String secondname=Name2.getText().toString().toLowerCase();
                firstSum = 0;
                secondSum = 0;
                loveSum = 0;
                for (int i = 0; i < firstname.length(); i++) {
                    firstSum += firstname.charAt(i);
                }
                for (int i = 0; i < secondname.length(); i++) {
                    secondSum += secondname.charAt(i);
                }
                for (int i = 0; i < LOVE.length(); i++)
                {
                    loveSum += LOVE.charAt(i);
                }
                totalSum = findSum(firstSum + secondSum);
                loveSum = findSum(loveSum);
                if (totalSum > loveSum) {
                    totalSum = loveSum - (totalSum - loveSum);
                }
                textView.setText("Your Love Percentage - "+(totalSum*100/loveSum)+"%");
                output=totalSum*100/loveSum;
                if(output<=10)
                {
                    result.setText("Waste Of Time");
                }
                else if(output>10&&output<=20)
                {
                    result.setText("You are Brothers and Sisters");
                }
                else if(output>20 && output<=30)
                {
                    result.setText("Don't Waste The Time By Loving Him / Her");
                }
                else if(output>31 && output<=40)
                {
                    result.setText("You Are Friends Don't Try To Love");
                }
                else if(output>41&&output<=50)
                {
                    result.setText("He/She Doesn't Loves You");
                }
                else if(output>50 && output<=70)
                {
                    result.setText("Be More FaithFull to Him / Her");
                }
                else if(output>70 && output<=80)
                {
                    result.setText("Good Realtionship");
                }
                else if(output>80 && output<=90)
                {
                    result.setText("Don't Miss Him / Her.");
                }
                else {
                    result.setText("He / she is Your Future Partner!!");
                }
                exit=findViewById(R.id.exit);
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      /* Intent i=new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_TEXT,firstname+" and "+secondname+" Your Love Percentage is "+output+"%");
                        startActivity(Intent.createChooser(i,"Share Via"));*/
                        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
                        Bitmap bitmap=takeScreenshot();
                        saveBitmap(bitmap);
                        shareit();
                    }
                });


            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name1.setText("");
                Name2.setText("");
                textView.setText("");
                result.setText("");
            }
        });
    }
    private static int findSum(int no) {
        int sum = 0;
        while (no > 0) {
            sum += no % 10;
            no /= 10;
        }
        return sum;
    }
    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }
    public void saveBitmap(Bitmap bitmap) {
         imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }
    private void shareit() {
        Uri uri = Uri.fromFile(imagePath);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String shareBody = "Our Love Percentage..";
        //String body=String.valueOf(output);
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Love Percenage");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {


            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[] { permission },
                    requestCode);
        }
    }
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "STORAGE PERMISSON GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
