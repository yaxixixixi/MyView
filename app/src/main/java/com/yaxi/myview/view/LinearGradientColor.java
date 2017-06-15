package com.yaxi.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yaxi on 2017/4/12.
 */

public class LinearGradientColor extends View {

    private String Text = "";
    private Paint mPaint;
    private LinearGradient mShader;
    private Rect rect;

    public LinearGradientColor(Context context) {
        super(context);
        init();
    }

    public LinearGradientColor(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LinearGradientColor(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShader = new LinearGradient(0,0,0,300,new int[]{
                0xffff0000,
                0xffffff00,
                0xff00ff00,
                0xff00ffff,
                0xff0000ff,
                0x880000ff},null, Shader.TileMode.CLAMP);
        mPaint.setShader(mShader);
        rect = new Rect(0,0,500,300);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(rect,mPaint);
    }


}
