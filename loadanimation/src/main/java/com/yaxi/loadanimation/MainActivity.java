package com.yaxi.loadanimation;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mFrameLayout;
    private ContentView mContentView;
    private SplashView mSplashView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mFrameLayout = new FrameLayout(this);
        mContentView = new ContentView(this);
        mFrameLayout.addView(mContentView);

        mSplashView = new SplashView(this);
        mFrameLayout.addView(mSplashView);


        setContentView(mFrameLayout);

        startSplashDataLoad();
    }

    Handler mHandler = new Handler();

    private void startSplashDataLoad() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //开启加载数据  ----数据加载前,开启动画完毕后开启进场动画
                mSplashView.splashDisappear();

            }
        }, 2000);

    }
}
