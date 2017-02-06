package com.yaxi.loadanimation;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by yaxi on 2017/1/13.
 */

public class MyActivity extends AppCompatActivity {

    private LoadAnim loadAnim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        findViewById(R.id.img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyActivity.this, "you have clicked me ", Toast.LENGTH_SHORT).show();
            }
        });
        loadAnim = (LoadAnim) findViewById(R.id.loadanim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               loadAnim.splashController();
            }
        },500);
    }


}
