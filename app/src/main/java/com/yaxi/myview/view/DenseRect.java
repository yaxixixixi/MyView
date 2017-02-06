package com.yaxi.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yaxi on 2017/1/17.
 */

public class DenseRect extends View {

    private Paint mPaint = new Paint();

    private int mWidth;
    private int mHeight;


    public DenseRect(Context context) {
        this(context,null);
    }

    public DenseRect(Context context, AttributeSet attrs) {
        super(context, attrs);

        initPaint();
    }

    private void initPaint() {
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5F);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    RectF rect = new RectF(-300,-300,300,300);
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth/2,mHeight/2);

        canvas.drawRect(rect,mPaint);

        for (int i = 0;i<36; i++) {
            canvas.scale(0.9f,0.9f);
            canvas.rotate(10);
            canvas.drawRect(rect,mPaint);
        }


    }
}
