package com.example.converttool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout distanceBLock, timeBlock, weightBlock, temperatureBlock;
    public static final int DISTANCE_CONVERT = 0;
    public static final int WEIGHT_CONVERT = 1;
    public static final int TEMPERATURE_CONVERT = 2;
    public static final int TIME_CONVERT = 3;
    private int convertType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        distanceBLock = (LinearLayout) findViewById(R.id.distance_convert);
        timeBlock = (LinearLayout) findViewById(R.id.time_convert);
        weightBlock = (LinearLayout) findViewById(R.id.weight_convert);
        temperatureBlock = (LinearLayout) findViewById(R.id.temperature_convert);

        distanceBLock.setOnClickListener(this);
        temperatureBlock.setOnClickListener(this);
        weightBlock.setOnClickListener(this);
        timeBlock.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if(id == distanceBLock.getId()) {
            convertType = DISTANCE_CONVERT;
        }
        if(id == timeBlock.getId()) {
            convertType = TIME_CONVERT;
        }
        if(id == weightBlock.getId()) {
            convertType = WEIGHT_CONVERT;
        }
        if(id == temperatureBlock.getId()) {
            convertType = TEMPERATURE_CONVERT;
        }
        Intent intent = new Intent(this, ConvertActivity.class);
        intent.putExtra("convert_type", convertType);
        startActivity(intent);
    }
}