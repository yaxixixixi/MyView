package com.yaxi.matrixc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private MatrixC matrixC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        matrixC = (MatrixC) findViewById(R.id.matrix_c);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public static void main (String[] args){

    }
}
