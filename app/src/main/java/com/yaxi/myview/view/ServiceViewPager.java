package com.yaxi.myview.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by icursoft on 2017/2/24.
 */

public class ServiceViewPager extends RelativeLayout {

    private static final String TAG = ServiceViewPager.class.getSimpleName();
    private Context context;
    private int mCurrentIndex = 0;//当前页的下标
    private int mFrameLayoutPages;//FrameLayout的页数
    private List<FrameLayout> pages;//存放所有FrameLayout页的集合
    private List<? extends View> views;
    private float mWidth;
    private boolean isAnimEnd = true; //动画是否播放完毕
    private TranslationListener listener;//平移动画的生命周期监听器


    public ServiceViewPager(Context context) {
        this(context,null);
    }

    public ServiceViewPager(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public ServiceViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    private void initView() {
        pages = new ArrayList<>();

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        for (int i = 0; i < mFrameLayoutPages; i++) {
            FrameLayout frameLayout = new FrameLayout(context);
            pages.add(i,frameLayout);
            ServiceViewPager.this.addView(frameLayout,params);
        }

        this.post(mRunnable);
    }

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mWidth = getWidth();
            for (int i = 1; i < mFrameLayoutPages; i++) {
                pages.get(i).setX(mWidth); // 为除去首页的所有页面设置起始位置
            }
        }
    };

    public void setPageContent(List<? extends View> views){
        this.views = views;
        if (views == null)
            throw new NullPointerException("为viewpager设置的views为空");
        if (views.size() <1) {
            Log.i(TAG, "setPageContent: 为viewpager设置的views为空数据！view.size() = 0");
            return;
        }
        setFrameLayoutPages(views.size());

        for (int i = 0; i < views.size(); i++) {
            setPageContent(views.get(i),i);
        }
    }

    /**
     * 为FrameLayout添加view ==> imageView or MyVideoVIew
     * @param view
     * @param index
     */
    private void setPageContent(View view,int index){
        pages.get(index).addView(view);
    }

    /**
     * 设置FrameLayout的页数
     * @param frameLayoutPages
     */
    public void setFrameLayoutPages(int frameLayoutPages){
        this.mFrameLayoutPages = frameLayoutPages;
        initView();

    }

    /**
     * 获取FrameLayout的页数
     * @return
     */
    public int getFrameLayoutPages(){
        return mFrameLayoutPages;
    }

    /**
     * 从左向右滑动
     */
    public void left2Right(){
        int nextIndex = mCurrentIndex == 0 ? mFrameLayoutPages - 1 : mCurrentIndex - 1;
        exchangePage(nextIndex,false);
    }

    /**
     * 从右向左滑动
     */
    public void right2Left(){
        int nextIndex = mCurrentIndex < mFrameLayoutPages - 1 ? mCurrentIndex + 1 : 0;
        exchangePage(nextIndex,true);
    }


    /**
     * 滑动切换页面的逻辑
     * @param nextIndex 下一个出现的页面下标
     * @param flag 标识滑动方向： true表示从右向左；false表示从左向右
     */
    private void exchangePage(final int nextIndex, boolean flag){
        if (!isAnimEnd)
            return;
        float target = flag ?  -mWidth : mWidth;
        AnimatorSet set = new AnimatorSet();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                isAnimEnd = false;
                if(listener != null)
                    listener.onTranslationStart(mCurrentIndex);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnimEnd = true;
                mCurrentIndex = nextIndex;
                if (listener != null)
                    listener.onTranslationEnd(mCurrentIndex);
            }
        });
        //根据flag设定下一个页面的位置
        if (flag)//从右向左
            pages.get(nextIndex).setX(mWidth);
        else
            pages.get(nextIndex).setX(-mWidth);

        ObjectAnimator currentAnim = ObjectAnimator.ofFloat(pages.get(mCurrentIndex),"x",target);
        ObjectAnimator nextAnim = ObjectAnimator.ofFloat(pages.get(nextIndex),"x",0);
        set.playTogether(currentAnim,nextAnim);
        set.start();
    }

    public interface TranslationListener {
        /**
         * @param currentIndex 当前页面的index
         */
        void onTranslationStart(int currentIndex);

        void onTranslationEnd(int currentIndex);
    }


    public void setTranslationListenner(TranslationListener listener){
        this.listener = listener;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        right2Left();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                right2Left();
                break;

        }
        return super.onTouchEvent(event);
    }
}
