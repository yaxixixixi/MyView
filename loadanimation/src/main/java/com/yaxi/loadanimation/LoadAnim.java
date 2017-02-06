package com.yaxi.loadanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by yaxi on 2017/1/13.
 */

public class LoadAnim extends View{

    private static final String TAG = LoadAnim.class.getSimpleName();
    private Paint mPaint,backgroundPaint;
    /**
     * 背景颜色
     */
    private int mSplashColor;
    /**
     * 空心圆的半径
     */
    private float holeRadius;
    /**
     * 小圆的颜色集
     */
    private int[] mCircleColors;
    /**
     * 公转动画
     */
    private ValueAnimator valueAnimator;
    /**
     * 小圆公转的角度
     */
    private float curOuterCircleRotateAngle;
    /**
     * 小圆公转半径
     */
    private float outerCircleRadius = 240;

    /**
     * 小圆的半径
     */
    private float innerCircleRadius = 20;
    /**
     * View 的X坐标
     */
    private float mCenterX;
    /**
     * View 的Y坐标
     */
    private float mCenterY;
    /**
     * View 的对角线的一半
     */
    private float mDiagonalDist;
    /**
     * 分发具体动画的控制器
     */
    private SplashState mSplashState;




    public LoadAnim(Context context) {
        this(context,null);
    }

    public LoadAnim(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadAnim(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mSplashColor = Color.WHITE;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setColor(mSplashColor);
        backgroundPaint.setStyle(Paint.Style.STROKE);



        mCircleColors = getResources().getIntArray(R.array.splash_circle_colors);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (mSplashState == null) {
            mSplashState = new RotationState();
        }
        mSplashState.drawState(canvas);
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterX = w / 2;
        mCenterY = h / 2;
        mDiagonalDist = (float)Math.sqrt(w * w + h * h)/2;
    }

    private abstract class SplashState{
        public abstract void drawState(Canvas canvas);
    }

    public void splashController(){
        if (mSplashState != null && mSplashState instanceof RotationState){
            RotationState rotationState = (RotationState) mSplashState;
            rotationState.cancle();
            post(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "run: 已经切换至聚合动画");
                    mSplashState = new MerginState();
                }
            });

        }else if (mSplashState == null){
            Log.e(TAG, "splashController: ", new NullPointerException(mSplashState.getClass().getName()+"is null." ));
        }else{
            Log.e(TAG, "splashController: ", new IllegalStateException("mSplashState is not instanceof RotationState"));
        }
    }

    /**
     * 小圆的旋转动画
     */
    private class RotationState extends SplashState{

        /*在构造方法里面创建视图 ，并衔接动画*/
        public RotationState(){
            valueAnimator = ValueAnimator.ofFloat(0, (float) (Math.PI * 2));
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    curOuterCircleRotateAngle = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            valueAnimator.setDuration(2000);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.start();
        }
        @Override
        public void drawState(Canvas canvas) {
            drawBackground(canvas);
            drawLittleCircle(canvas);
        }

        public void cancle(){
            valueAnimator.cancel();
        }

    }

    /**
     * 聚合动画
     */
    private class MerginState extends SplashState{

        public MerginState(){
            valueAnimator = ValueAnimator.ofFloat(0,outerCircleRadius);
            valueAnimator.setInterpolator(new OvershootInterpolator(20f));
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    outerCircleRadius = (float) animation.getAnimatedValue();
                    Log.i(TAG, "onAnimationUpdate: 当前的公转半径："+animation.getAnimatedValue());
                    postInvalidate();
                }
            });

            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                   mSplashState = new ExpandState();
                }
            });
            valueAnimator.setDuration(1000);
            valueAnimator.reverse();
        }

        @Override
        public void drawState(Canvas canvas) {
            drawBackground(canvas);
            drawLittleCircle(canvas);
        }
    }

    /**
     * 水波纹扩散动画
     */
    private class ExpandState extends SplashState{

        public ExpandState() {
            valueAnimator = ValueAnimator.ofFloat(0,mDiagonalDist);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    holeRadius = (float) animation.getAnimatedValue();
                    Log.i(TAG, "onAnimationUpdate: holeRadius:"+holeRadius);
                    postInvalidate();
                }
            });

            valueAnimator.setDuration(10000);
            valueAnimator.start();
        }



        @Override
        public void drawState(Canvas canvas) {
            drawBackground(canvas);
        }
    }

    private void drawLittleCircle(Canvas canvas) {
        float divideAngle = (float) (2*Math.PI/mCircleColors.length);//根据小圆的数量得到其夹角
        for (int i = 0; i < mCircleColors.length; i++) {
            float subCircleX = (float) (mCenterX + outerCircleRadius * Math.cos(divideAngle * i
                    + curOuterCircleRotateAngle));
            float subCircleY = (float) (mCenterY + outerCircleRadius * Math.sin(divideAngle * i
                    + curOuterCircleRotateAngle));
            mPaint.setColor(mCircleColors[i]);
            Log.i(TAG, "drawLittleCircle: subCircleX"+subCircleX);
            Log.i(TAG, "drawLittleCircle: subCircleY"+subCircleY);
            canvas.drawCircle(subCircleX,subCircleY,innerCircleRadius,mPaint);
        }
    }

    /**
     * 第一阶段  没有空心圆，只显示圆点动画  所以背景颜色设置为白色
     * 第二阶段  设置空心圆的渐变效果
     * @param canvas
     */
    private void drawBackground(Canvas canvas) {
        if (holeRadius > 0){
            float strkeWidth = mDiagonalDist - holeRadius;
            backgroundPaint.setStrokeWidth(strkeWidth);
            float radius = holeRadius + strkeWidth / 2;

            canvas.drawCircle(mCenterX,mCenterY,radius,backgroundPaint);
        }
        else
            canvas.drawColor(mSplashColor);
    }
}
