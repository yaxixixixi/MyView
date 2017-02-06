package com.yaxi.myview;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;

/**
 * Created by yaxi on 2017/1/3.
 */

public class Check extends AppCompatActivity {

    private CheckBox checkBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//        checkBox.setButtonTintList(getResources().getColorStateList(R.color.seletor_button_color));

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
//                    checkBox.setButtonDrawable(getResources().getDrawable(R.drawable.arma));
                }
                else{
//                    checkBox.setButtonDrawable(getResources().getDrawable(R.drawable.armd));
                }
            }
        });
    }
}
