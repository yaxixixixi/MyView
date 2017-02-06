package com.yaxi.loadanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by Wang on 2017/1/12.
 * <p>
 * 自绘控件
 */

public class SplashView extends View {

    private static final String TAG = SplashView.class.getSimpleName();

    private float mRotationRadius = 240;

    private float mCircleRadius = 40;

    private int[] mCircleColors;

    private long mRotationDuration = 1600;//ms


    //第二部分动画的执行总时间
    private long mSplashDuration = 800;
    //整体的背景颜色
    private int mSplashColor = Color.WHITE;

    private float mCurrentRotationAngle = 0F;

    private float mHoleRadius = 0F;
    private float mDiagonalDist;


    private ValueAnimator mValueAnimator;
    private Paint mPaint = new Paint();
    private Paint mPaintBackground = new Paint();

    private float mCurrentRotationRadius = 240;
    private float mCenterX;
    private float mCenterY;


    public SplashView(Context context) {
        this(context,null);
    }

    public SplashView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SplashView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(21)
    public SplashView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        mPaint.setAntiAlias(true);
        mPaintBackground.setAntiAlias(true);
        mPaintBackground.setStyle(Paint.Style.STROKE);
        mPaintBackground.setColor(mSplashColor);
        mCircleColors = getResources().getIntArray(R.array.splash_circle_colors);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2f;
        mCenterY = h / 2f;
        //屏幕对角线的一半
        mDiagonalDist = (float) Math.sqrt(w * w + h * h) / 2;

    }

    //设计模式:策略设计模式
    private SplashState mSplashState = null;


    @Override
    protected void onDraw(Canvas canvas) {

        //绘制动画:3个动画
        //绘制分发
        if (mSplashState == null) {
            mSplashState = new RotationState();
        }
        mSplashState.drawState(canvas);


        super.onDraw(canvas);

    }

    private abstract class SplashState {
        public abstract void drawState(Canvas canvas);
    }

    /*
    第一个动画 小圆旋转
     */
    private class RotationState extends SplashState {

        public RotationState() {
            //动画初始化
            //花1600ms,计算当前时刻的角度是多少:0~2π
            mValueAnimator = ValueAnimator.ofFloat(0, (float) Math.PI * 2);
            mValueAnimator.setInterpolator(new LinearInterpolator());
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //得到某个时间点计算的结果  这个时间点当前大圆旋转的角度
                    mCurrentRotationAngle = (float) animation.getAnimatedValue();
                    Log.i(TAG, "onAnimationUpdate: " + mCurrentRotationAngle);
                    postInvalidate();
                }
            });
            mValueAnimator.setDuration(mRotationDuration);
            mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mValueAnimator.start();
        }

        public void cancle() {
            mValueAnimator.cancel();
        }

        @Override
        public void drawState(Canvas canvas) {
            //绘制动画
            //1.擦黑板--绘制背景
            drawBackground(canvas);
            //2.绘制小圆--绘制小圆
            drawCircle(canvas);
        }
    }


    /*
    聚合动画
     */
    private class MerginState extends SplashState {

        public MerginState() {
            //花1600ms,计算当前时刻的半径是多少:r~0
            mValueAnimator = ValueAnimator.ofFloat(0, mRotationRadius);
            mValueAnimator.setInterpolator(new OvershootInterpolator(20f));
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //得到某个时间点计算的结果  这个时间点当前大圆的半径
                    mCurrentRotationRadius = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });


            mValueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                    mSplashState = new ExpandState();
                }
            });

            mValueAnimator.setDuration(mSplashDuration);
            mValueAnimator.reverse();

        }

        @Override
        public void drawState(Canvas canvas) {
            //第二个动画 改变公转的半径
            drawBackground(canvas);
            drawCircle(canvas);
        }
    }

    /*
    水波纹空心扩散动画  画空心圆环
    不断的控制画笔的宽度------空心圆的半径决定
     */
    private class ExpandState extends SplashState {

        public ExpandState() {
            //花800ms,计算当前空心圆的半径是多少:0~对角线一半
            mValueAnimator = ValueAnimator.ofFloat(0, mDiagonalDist);
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //得到某个时间点计算的结果  这个时间点当前空心圆的半径
                    mHoleRadius = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });


            mValueAnimator.setDuration(800);
            mValueAnimator.start();


        }

        @Override
        public void drawState(Canvas canvas) {
            drawBackground(canvas);
        }
    }


    public void splashDisappear() {
        //完毕后开进进场动画  1个动画结束 2,3动画开始
        if (mSplashState != null && mSplashState instanceof RotationState) {
            RotationState rotationState = (RotationState) mSplashState;
            rotationState.cancle();
            post(new Runnable() {
                @Override
                public void run() {
                    mSplashState = new MerginState();
                }
            });

        }

    }


    private void drawBackground(Canvas canvas) {


        if (mHoleRadius > 0) {
            //对角线的一半 - 空心圆的半径
            float strokeWidth = mDiagonalDist - mHoleRadius;
            mPaintBackground.setStrokeWidth(strokeWidth);
            //画圆的半径 = 空心圆的半径+画笔宽度/2
            float radius = mHoleRadius + strokeWidth / 2;
            canvas.drawCircle(mCenterX, mCenterY, radius, mPaintBackground);
        } else {
            //白色背景
            canvas.drawColor(mSplashColor);
        }
    }

    private void drawCircle(Canvas canvas) {
        float rotationAngle = (float) (2 * Math.PI / mCircleColors.length);
        for (int i = 0; i < mCircleColors.length; i++) {

            float cx = (float) (mCurrentRotationRadius * Math.cos(mCurrentRotationAngle + rotationAngle * i) + mCenterX);
            float cy = (float) (mCurrentRotationRadius * Math.sin(mCurrentRotationAngle + rotationAngle * i) + mCenterY);

            Log.i(TAG, "drawCircle: cx=" + cx + "&cy=" + cy);
            mPaint.setColor(mCircleColors[i]);
            canvas.drawCircle(cx, cy, mCircleRadius, mPaint);
        }
    }
}
