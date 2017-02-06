package com.yaxi.raderview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 *
 * 这种视图必须要实现onDraw方法，没有其他形式帮我们实现这种效果，必须自己去画
 * 选择正圆
 * Created by yaxi on 2016/12/30.
 */

public class RadarView extends View {


    private static final String TAG = RadarView.class.getSimpleName();
    private Paint circlePaint = null;
    private Paint shaderPaint = null;
    private SweepGradient sweepGradient;
    private Matrix mMatrix;
    private int width;
    private int height;
    private int diameter;

    public RadarView(Context context) {
        this(context,null);
    }

    public RadarView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRadar();
//        handler.post(task);
    }


    private void initRadar() {
        //新建一支画圆的画笔
        circlePaint = new Paint();
        //设置画笔颜色
        circlePaint.setColor(Color.BLUE);
        //设置画笔线条宽度
        circlePaint.setStrokeWidth(3);
        //设置抗锯齿
        circlePaint.setAntiAlias(true);
        //设置风格样式为线条
        circlePaint.setStyle(Paint.Style.STROKE);

        //扫描线的画笔
        shaderPaint = new Paint();
        shaderPaint.setColor(Color.RED);
        shaderPaint.setAntiAlias(true);
        shaderPaint.setStyle(Paint.Style.FILL);

        mMatrix = new Matrix();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure() called with: widthMeasureSpec = [" + widthMeasureSpec + "], heightMeasureSpec = [" + heightMeasureSpec + "]");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout() called with: changed = [" + changed + "], left = [" + left + "], top = [" + top + "], right = [" + right + "], bottom = [" + bottom + "]");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = this.getMeasuredWidth();
        height = this.getMeasuredHeight();
        //我们以宽高最小的一条边作为直径
        diameter = width > height ? height : width;
        //画圆、
        canvas.drawCircle(width /2, height /2, diameter /8,circlePaint);
        canvas.drawCircle(width /2, height /2, diameter /4,circlePaint);
        canvas.drawCircle(width /2, height /2,3 * diameter /8,circlePaint);
        canvas.drawCircle(width /2, height /2, diameter /2,circlePaint);
        // 直线
        canvas.drawLine((width - diameter)/2, height /2,(width + diameter), height /2,circlePaint);
        canvas.drawLine(width /2,(height - diameter)/2, width /2,(height + diameter)/2,circlePaint);

        //创建扫描渲染渐变对象
        if (sweepGradient == null)
        sweepGradient = new SweepGradient(width / 2, height / 2, Color.TRANSPARENT, Color.RED);

        shaderPaint.setShader(sweepGradient);
        canvas.concat(mMatrix);
        canvas.drawCircle(width /2, height /2, diameter /2,shaderPaint);

        Log.d(TAG, "onDraw() called with: canvas = [" + canvas + "]");
    }

    int start = 0;
    Handler handler = new Handler();
    Runnable task = new Runnable() {
        @Override
        public void run() {
            start++;
            mMatrix = new Matrix();
            mMatrix.setRotate(start,width/2,height/2);
            //刷新ui
            postInvalidate();
            start = start == 360?0:start;
            postDelayed(this,10);
            Log.i(TAG, "run: start = '"+start+"'");
        }
    };


    public void startTask(){
        handler.post(task);
    }
    public void stopTask(){
        this.removeCallbacks(task);
    }
    public void clearContent(){
        mMatrix.setRotate(0,width/2,height/2);
        stopTask();
        //刷新ui
        postInvalidate();
    }
}
