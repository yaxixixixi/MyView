package com.yaxi.myview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yaxi on 2017/9/27.
 */

public class ProgressView extends View {
    private static String TAG = ProgressView.class.getSimpleName();

    private Paint focus;
    private Paint normal;
    private RectF mRectF;
    private int mWidth = 500,mHeight = 100;


    public ProgressView(Context context) {
        super(context);
        initView();
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        initView();
    }

    private void initView() {
        focus = new Paint();
        focus.setColor(Color.GRAY);

        normal = new Paint();
        normal.setColor(Color.BLUE);

        mRectF = new RectF(0,0,mWidth,mHeight);


    }


    /**
     *
     * @param widthMeasureSpec Horizontal space requirements as imposed by the
     *        parent
     * @param heightMeasureSpec Vertical space requirements as imposed by the
     *        parent
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureSpec(true, widthMeasureSpec),measureSpec(false,heightMeasureSpec));

    }


    private int measureSpec(boolean isWidth,int spec){
        int mode = MeasureSpec.getMode(spec);
        int size = MeasureSpec.getSize(spec);
//        int padding = isWidth ? getPaddingLeft() + getPaddingRight() :
//                getPaddingTop() + getPaddingBottom();
//        int size = Math.max(0,size - padding);
        int resultSize = 0;
        // Note:size 是父控件即ViewGroup去掉自身的边距（调用measureChild）
        // （可能会调用measureChildWithMargins，这时会把view自身的margins计算进去，即去掉margins）
        // 以后的到的可以允许的最大值（此值>=0），在EXACTLY模式下（parentView不限制），
        // 直接使用size（parentView直接取view自身的layoutparams得到的值）作为自身的size；
        // 在AT_MOST模式下，size用的是parentView可以允许的最大值，此时如果view内部对与size有要求则选取最小值
        switch(mode){
            case MeasureSpec.EXACTLY://精确模式  包括确定值和match_parent
                resultSize = size;
                break;
            case MeasureSpec.AT_MOST://wrap_content,但是不能超过父View的大小
                resultSize = isWidth ? Math.min(mWidth,size) : Math.min(mHeight,size);
                break;
            case MeasureSpec.UNSPECIFIED:
                resultSize = isWidth ? mWidth:mHeight;
                break;
        }

        return resultSize;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawRoundRect();
    }
}
