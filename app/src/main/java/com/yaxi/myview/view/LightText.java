package com.yaxi.myview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
;


/**
 * Created by yaxi on 2017/4/12.
 */

public class LightText extends AppCompatTextView {

    private Paint mPaint;
    private LinearGradient mShader;
    private int w,h;
    private Matrix matrix;
    private int mVx;

    public LightText(Context context) {
        super(context);
        init();
    }

    public LightText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LightText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = getPaint();
        matrix = new Matrix();
        mShader = new LinearGradient(-2*w,0,-w,0,new int[]{
                getCurrentTextColor(),
                Color.RED,Color.BLUE,
                Color.GREEN,
                getCurrentTextColor()},null, Shader.TileMode.CLAMP);
        mPaint.setShader(mShader);

        ValueAnimator animator = ValueAnimator.ofInt(0,w * 3);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mVx = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(-1);
        animator.setDuration(2000L);
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        matrix.reset();
        matrix.preTranslate(mVx,0);
        mShader.setLocalMatrix(matrix);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
    }
}
