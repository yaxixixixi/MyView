package com.yaxi.myview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 在ScrollView中设置wrap_content，不会显示VIew，父布局已经不提供任何多余的位置
 * Created by yaxi on 2016/12/22.
 */

public class MeasuredView extends View {


    private static final String TAG = MeasuredView.class.getSimpleName();

    public MeasuredView(Context context) {
        super(context);
    }

    public MeasuredView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasuredView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec));
    }


    private int measureWidth(int widthMeasureSpec){
        int result = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        Log.i(TAG, "measureWidth: " +size);

        if (mode == MeasureSpec.EXACTLY){
            result = size;
        }else if (mode == MeasureSpec.AT_MOST){
            result = 400;//猜测：size是设置为wrap_content的时候相对于parentView的最大值
                        //这里设置的默认值是400，那么如果超过parentView的限制就使用限制值，
                        //否则，使用默认值即可
            result = Math.min(result,size);
        }
        return result;
    }

    private int measureHeight(int heightMeasureSpec){
        int result = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        switch (mode){
            case MeasureSpec.EXACTLY:
                Log.i(TAG, "measureWidth: MeasureSpec.EXACTLY");
                result = size;
                break;
            // Note:size 是父控件即ViewGroup去掉自身的边距（调用measureChild）
            // （可能会调用measureChildWithMargins，这时会把view自身的margins计算进去）
            // 以后的到的可以允许的最大值（此值>=0），在EXACTLY模式下（parentView不限制），
            // 直接使用size（parentView直接取view自身的layoutparams得到的值）作为自身的size；
            // 在AT_MOST模式下，size用的是parentView可以允许的最大值，此时如果view内部对与size有要求则选取最小值
            case MeasureSpec.AT_MOST:
                Log.i(TAG, "measureWidth: MeasureSpec.AT_MOST");
                result = Math.min(400,size);
                break;
            default:
                break;
        }
        return result;
    }
}
