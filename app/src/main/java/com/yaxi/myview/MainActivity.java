package com.yaxi.myview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: ");
    }


    public void open(View view){
        startActivity(new Intent(MainActivity.this,ExpandableListActivity.class));

    }

    public void checkbox(View view){
        startActivity(new Intent(MainActivity.this,Check.class));
    }


}
