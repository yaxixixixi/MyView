package com.yaxi.photopicker.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yaxi on 2017/1/9.
 */

public class GridItemDecoration extends RecyclerView.ItemDecoration{
    private Context context;
    private int mOrientation;
    private Drawable mDivider;
    private int limitLine;

    private static final int DIRECTION_HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    private static final int DIRECTION_VERTICAL = LinearLayoutManager.VERTICAL;

    public static final int[] attrs = new int[]{android.R.attr.listDivider};

    public GridItemDecoration(Context context, int mOrientation,int limitLine) {
        this.context = context;
        this.limitLine = limitLine;
        TypedArray ta = context.obtainStyledAttributes(attrs);
        mDivider = ta.getDrawable(0);
        setOrientation(mOrientation);
    }

    private void setOrientation(int mOrientation) {
        if (mOrientation != DIRECTION_HORIZONTAL && mOrientation != DIRECTION_VERTICAL){
            throw new IllegalStateException(getClass().getName()+":invalid orientation.");
        }
        this.mOrientation = mOrientation;
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
        int totalCount = parent.getChildCount();
        int surplusCount = totalCount % limitLine;
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount() - 1;
        int
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int botton = top + mDivider.getIntrinsicHeight();

        }
    }

    public void drawVerticalLine(Canvas c, RecyclerView parent){
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        int totalCount = layoutManager.getItemCount();
        int surplusCount = totalCount % layoutManager.getSpanCount();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int position = parent.getChildAdapterPosition(child);

        }



    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        switch(mOrientation){
            case DIRECTION_HORIZONTAL:
                outRect.set(0,0,0,mDivider.getIntrinsicHeight());
                break;
            case DIRECTION_VERTICAL:
                outRect.set(0,0,mDivider.getIntrinsicHeight(),0);
                break;
        }
    }
}
