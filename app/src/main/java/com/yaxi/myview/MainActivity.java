package com.yaxi.myview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.yaxi.myview.view.ServiceViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "mainActivity";
    private ServiceViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: ");


        pager = (ServiceViewPager) findViewById(R.id.service_viewpager);

        ImageView view1 = new ImageView(this);
        ImageView view2 = new ImageView(this);
        ImageView view3 = new ImageView(this);
        ImageView view4 = new ImageView(this);
        ImageView view5 = new ImageView(this);

        view1.setImageResource(R.drawable.view);
        view2.setImageResource(R.drawable.view3);
        view3.setImageResource(R.drawable.arma);
        view3.setImageResource(R.drawable.armd);
        view3.setImageResource(R.drawable.view3);

        List<View> list = new ArrayList<>();
        list.add(view1);
        list.add(view2);
        list.add(view3);
        list.add(view4);
        list.add(view5);

        pager.setPageContent(list);
    }


    public void open(View view){
        startActivity(new Intent(MainActivity.this,ExpandableListActivity.class));

    }

    public void checkbox(View view){
        startActivity(new Intent(MainActivity.this,Check.class));
    }

    public void main(){
        Log.i(TAG, "main: ");
    }

}
