package com.yaxi.matrixc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yaxi on 2017/1/13.
 */

public class MatrixC extends View{

    private Paint mPaint;

    private float mWidth;
    private float mHeight;
    private Matrix matrix;
    private float mCenterX,mCenterY;

    public MatrixC(Context context) {
        this(context,null);
    }

    public MatrixC(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MatrixC(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(4f);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                start ++;

                matrix = new Matrix();
                if (start%2 == 0)
                matrix.setRotate(start,mWidth/2,mCenterY);
//                matrix.setScale(0.1f,start/100);
                else
                matrix.setTranslate(0,start*2);
                mCenterY = mHeight/2+start*2;
                postInvalidate();
                start = start==360?0:start;
                postDelayed(this,10);
            }
        },10);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.concat(matrix);
        canvas.drawLine(mWidth/3,mHeight/2,mWidth*2/3,mHeight/2,mPaint);


    }
    int start = 0;

    Handler handler = new Handler();



}
