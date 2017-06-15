package com.yaxi.myview.view;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.yaxi.myview.R;

/**
 * Created by yaxi on 2017/3/23.
 */

public class ShadowTextView extends TextView {

    private static final String TAG = ShadowTextView.class.getSimpleName();
    private Paint bgPaint = new Paint();
    private String text = "祝您生活愉快";

    private int mWidth,mHeight;
    private RectF rectF;

    private int different = 3;

    public ShadowTextView(Context context) {
        this(context,null);
    }

    public ShadowTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShadowTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }


    public void init(){
        Log.i(TAG, "init: ");

        int color = Color.parseColor("#555555");
        bgPaint.setColor(color);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setStrokeWidth(5);
        bgPaint.setAntiAlias(false);
        BlurMaskFilter maskFilter = new BlurMaskFilter(30, BlurMaskFilter.Blur.NORMAL);
        bgPaint.setMaskFilter(maskFilter);


    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        Log.i(TAG, "setText: ");
        super.setText(text, type);
        this.text = text.toString();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        Log.i(TAG, "onSizeChanged: w = "+w+"\n h = "+h);
        rectF = new RectF(0+different,0+different,mWidth-different,mHeight-different);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "onDraw: ");
        canvas.drawRoundRect(rectF,50,50,bgPaint);
        super.onDraw(canvas);
    }
    
}
