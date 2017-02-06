package com.yaxi.loadanimation;


import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Wang on 2017/1/12.
 */

public class ContentView extends ImageView {
    public ContentView(Context context) {
        super(context);

        setScaleType(ScaleType.FIT_XY);
        setImageResource(R.drawable.pic_33);
    }
}
