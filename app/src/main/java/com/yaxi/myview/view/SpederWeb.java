package com.yaxi.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 蜘蛛网图
 * Created by yaxi on 2017/1/18.
 */

public class SpederWeb extends View {

    private static final String TAG = SpederWeb.class.getSimpleName();
    private int mWidth,mHeight;
    private Paint mPaint = new Paint();
    private Paint shadowPaint = new Paint();

    public SpederWeb(Context context) {
        this(context,null);
    }

    public SpederWeb(Context context, AttributeSet attrs) {
        this(context, attrs,0);


    }

    public SpederWeb(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        shadowPaint.setColor(Color.BLUE);
        shadowPaint.setStyle(Paint.Style.FILL);
        shadowPaint.setStrokeWidth(9);
        shadowPaint.setAlpha(166);


    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        if (mWidth < 50 || mHeight < 50)
            throw new IllegalStateException("the width of view is too little");

        float r = mWidth < mHeight ? mWidth/2:mHeight/2 - 50;

        canvas.translate(mWidth /2,mHeight/2);

        Path path = new Path();
        path.lineTo(r,0);
        path.moveTo(0,0);
        path.lineTo(-r,0);
        path.moveTo(0,0);
        path.lineTo(r/2, (float) (-r*Math.sin(Math.PI/3)));
        path.moveTo(0,0);
        path.lineTo(r/2, (float) (r*Math.sin(Math.PI/3)));
        path.moveTo(0,0);
        path.lineTo(-r/2, (float) (-r*Math.sin(Math.PI/3)));
        path.moveTo(0,0);
        path.lineTo(-r/2, (float) (r*Math.sin(Math.PI/3)));


        //正多边形
        for (int i = 0; i < 5; i++) {
            float subr = r/5*(i+1);
            path.moveTo(subr,0);
            for (int j = 0; j < 5; j++) {
                path.lineTo((float)(subr*Math.cos(Math.PI/3*(j+1))),(float)(subr*Math.sin(Math.PI/3*(j+1))));
            }
            path.close();
        }

        canvas.drawPath(path,mPaint);

        Path shadow = new Path();

        shadow.moveTo(r,0);
        shadow.lineTo((float)(4.0/5*r*Math.cos(Math.PI/3)),(float)(4.0/5*r*Math.sin(Math.PI/3)));
        shadow.lineTo((float)(2.0/5*r*Math.cos(Math.PI/3*2)),(float)(2.0/5*r*Math.sin(Math.PI/3*2)));
        shadow.lineTo((float)(3.0/5*r*Math.cos(Math.PI)),(float)(3.0/5*r*Math.sin(Math.PI)));
        shadow.lineTo((float)(5.0/5*r*Math.cos(Math.PI/3*4)),(float)(5.0/5*r*Math.sin(Math.PI/3*4)));
        shadow.lineTo((float)(4.0/5*r*Math.cos(Math.PI/3*5)),(float)(4.0/5*r*Math.sin(Math.PI/3*5)));
        shadow.close();

        Log.i(TAG, "onDraw: ");

        canvas.drawPath(shadow,shadowPaint);





    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
    }
}
