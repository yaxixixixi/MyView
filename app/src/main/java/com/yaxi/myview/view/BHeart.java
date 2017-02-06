package com.yaxi.myview.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yaxi on 2017/1/18.
 */

public class BHeart extends View {

    private float c = 0.551915024494f;

    private int mCenterX,mCenterY;

    private Paint mPaint = new Paint();



    public BHeart(Context context) {
        this(context,null);
    }

    public BHeart(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BHeart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {

        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w;
        mCenterY = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mCenterX/2,mCenterY/2);

        Path  path = new Path();
        path.moveTo(0,300);
        path.cubicTo(300*0.4f,300*0.6f,300,300*c,300,0);
        path.cubicTo(300,-300*c,300*c,-300,0,-100);
        path.cubicTo(-300*c,-300,-300,-300*c,-300,0);
        path.cubicTo(-300,300*c,-300*0.4f,300*0.6f,0,300);
        path.close();


        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path,mPaint);

        canvas.translate(-500,800);

        Path  heart = new Path();
        heart.moveTo(131.6f*5,190.5f*5);
        heart.quadTo(108.7f*5,163.7f*5,134.1f*5,127.4f*5);
        heart.cubicTo(159.8f*5,91.4f*5,165.8f*5,66.7f*5,167.2f*5,56.1f*5);
        heart.cubicTo(168.6f*5,45.9f*5,165.8f*5,14.8f*5,133.4f*5,12.3f*5);
        heart.cubicTo(101.2f*5,10.6f*5,97.4f*5,35.3f*5,93.1f*5,64.9f*5);
        heart.quadTo(86.1f*5,8.8f*5,48*5,16.2f*5);
        heart.cubicTo(9.5f*5,23.3f*5,19.4f*5,62.1f*5,19.8f*5,65.3f*5);
        heart.cubicTo(20.5f*5,69.1f*5,23.6f*5,88.2f*5,59.6f*5,127*5);
        heart.quadTo(96*5,166.5f*5,131.6f*5,190.5f*5);




        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawPoint(0,0,mPaint);
        canvas.drawPath(heart,mPaint);





    }
}
