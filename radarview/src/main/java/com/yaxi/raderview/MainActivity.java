package com.yaxi.raderview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yaxi.raderview.view.RadarView;

public class MainActivity extends AppCompatActivity {

    private RadarView rdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rdView = (RadarView) findViewById(R.id.radarView);
    }



    public void start(View view){
        rdView.startTask();
    }
    public void stop(View view){
        rdView.stopTask();
    }
    public void clear(View view){
        rdView.clearContent();
    }
}
