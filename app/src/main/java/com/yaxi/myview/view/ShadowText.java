package com.yaxi.myview.view;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Xfermode;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yaxi.myview.R;

/**
 * Created by icursoft on 2017/3/22.
 */

public class ShadowText extends View {

    private static final String TAG = ShadowText.class.getSimpleName();
    private Paint charPaint = new Paint();
    private Paint bgPaint = new Paint();
    private SweepGradient sweepGradient,sweepGradient1;
    private ComposeShader composeShader;
    private String text = "祝您生活愉快";
    private boolean selected = false;

    private char[] chars;
    private int mWidth,mHeight;


    public ShadowText(Context context) {
        this(context,null);
    }

    public ShadowText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShadowText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        Log.i(TAG, "init: ");
        charPaint.setColor(Color.WHITE);
        charPaint.setStrokeWidth(1);
        charPaint.setTextSize(60);
        charPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        bgPaint.setColor(Color.GRAY);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setStrokeWidth(5);
        bgPaint.setAntiAlias(false);
        BlurMaskFilter maskFilter = new BlurMaskFilter(30, BlurMaskFilter.Blur.NORMAL);
        bgPaint.setMaskFilter(maskFilter);

//        Xfermode xFermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OVER);
//        bgPaint.setXfermode(xFermode);

        chars = text.toCharArray();
        sweepGradient = new SweepGradient(mWidth / 2, mHeight / 2, getResources().getColor(R.color.cus), getResources().getColor(R.color.cus));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure: ");
        setMeasuredDimension(measureSpecWidth(widthMeasureSpec),measureSpecHeight(heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        Log.i(TAG, "onSizeChanged: w = "+w+"\n h = "+h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: ");
        canvas.drawColor(Color.BLACK);
//        int[] colors = new int[]{Color.RED,Color.BLUE};
//        float[] positions = new float[]{0,1.1f};

//        sweepGradient1 = new SweepGradient(mWidth / 2, mHeight / 2,colors,positions);
//        composeShader = new ComposeShader();
        bgPaint.setShader(sweepGradient);

        Rect bounds = new Rect();
        charPaint.getTextBounds(text,0,chars.length,bounds);
        Paint.FontMetricsInt fontMetricsInt = charPaint.getFontMetricsInt();


        for (int i = 0; i < chars.length; i++) {
            canvas.drawCircle(50+30+60*i,300 + fontMetricsInt.bottom + (fontMetricsInt.top - fontMetricsInt.bottom)/2,0-(fontMetricsInt.top - fontMetricsInt.bottom)/2,bgPaint);
            canvas.drawText(chars,i,1 ,50+60*i,300,charPaint);

            int bottom = bounds.bottom;
            int top = bounds.top;
            Log.i(TAG, "onDraw: bottom= "+bottom);
            Log.i(TAG, "onDraw: top= "+top);
            Log.i(TAG, "onDraw: height= "+bounds.height());


        }


    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i(TAG, "onLayout: ");
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.i(TAG, "onFinishInflate: ");
    }

    private int measureSpecWidth(int widthMeasureSpec){
        int result = 500;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        switch(mode){
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result = result > size ? size :result;
                break;
        }
        return result;
    }

    private int measureSpecHeight(int heightMeasureSpec){
        int result = 400;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        switch (mode){
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result = result > size ? size :result;
                break;
        }
        return result;
    }

    public void setText(String text){
        this.text = text;
        Log.i(TAG, "setText: ");
    }

    @Override
    public void setSelected(boolean selected){
        this.selected = selected;
        Log.i(TAG, "setSelected: ");
    }
}
