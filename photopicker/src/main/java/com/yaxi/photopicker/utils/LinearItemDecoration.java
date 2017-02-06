package com.yaxi.photopicker.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yaxi on 2017/1/9.
 */

public class LinearItemDecoration extends RecyclerView.ItemDecoration {

    private Context context;
    private Drawable mDivider;
    private int mOrientation;

    private static final int DIRECTION_HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    private static final int DIRECTION_VERTICAL = LinearLayoutManager.VERTICAL;

    public static final int[] attrs = new int[]{android.R.attr.listDivider};


    public LinearItemDecoration(Context context, int mOrientation) {
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs);
        this.mDivider = ta.getDrawable(0);
        ta.recycle();
        setOrientation(mOrientation);
    }

    public void setOrientation(int orientation){
        if (orientation != DIRECTION_HORIZONTAL && orientation != DIRECTION_VERTICAL){
            throw new IllegalStateException(getClass().getName()+":invalid orientation");
        }
        this.mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        switch (mOrientation){
            case DIRECTION_HORIZONTAL:
                drawHorizontalLine(c,parent);
                break;
            case DIRECTION_VERTICAL:
                drawVerticalLine(c,parent);
                break;
        }
    }

    public void drawHorizontalLine(Canvas c, RecyclerView parent){
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount() - 1;
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom()+params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    public void drawVerticalLine(Canvas c, RecyclerView parent){
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount() - 1;
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + params.rightMargin;
            int right = left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        switch (mOrientation){
            case DIRECTION_HORIZONTAL:
                outRect.set(0,0,0,mDivider.getIntrinsicHeight());
                break;
            case DIRECTION_VERTICAL:
                outRect.set(0,0,mDivider.getIntrinsicHeight(),0);
        }
    }
}
