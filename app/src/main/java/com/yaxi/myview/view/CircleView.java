package com.yaxi.myview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.yaxi.myview.R;

/**
 * hehe,不能用
 * Created by yaxi on 2016/12/30.
 */

public class CircleView extends ImageView {


    private static final String TAG = CircleView.class.getSimpleName();

    private final RectF mDrawableRect = new RectF();
    private final RectF mBorderRect = new RectF();
    private Matrix mShaderMatrix = new Matrix();
    private int DEFAULT_BORDER_WIDTH = 0;
    private int DEFAULT_BORDER_COLOR = Color.BLACK;
    private static final ScaleType MY_SCALE_TYPE = ScaleType.CENTER_CROP;
    /**
     * 圆边框画笔
     */
    private Paint mPaint;
    /**
     * 需要设置的图片
     */
    private Bitmap mBitmap;
    /**
     * 图片画笔
     */
    private Paint imgPaint;
    /**
     * View的中心点X坐标
     */
    private int mCenterX;
    /**
     * View的中心点Y坐标
     */

    private int mCenterY;
    /**
     * 边界宽度
     */
    private int mBorderWidth = DEFAULT_BORDER_WIDTH;
    /**
     * 边界的颜色
     */
    private int mBorderCorlor;
    /**
     * 设置图片形状，具体查看BitmapShader.txt文件
     */
    private BitmapShader mBitmapShader;
    /**
     * bitmap图片的宽度
     */
    private int mBitmapWidth;
    /**
     * bitmap图片的高度
     */
    private int mBitmapHeight;
    /**
     * View的宽高最小值 去掉 border以后的半径
     */
    private int mBorderRadius;
    /**
     *
     */
    private int mDrawableRadius;





    public CircleView(Context context) {
        this(context,null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.setScaleType(MY_SCALE_TYPE);
        init();
    }

    private  void init() {
        Log.i(TAG, "init: ");
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(DEFAULT_BORDER_COLOR);
        mPaint.setStrokeWidth(DEFAULT_BORDER_WIDTH);
        mPaint.setStyle(Paint.Style.STROKE);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.view);
        if (mBitmap == null) {
            return;
        }
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        imgPaint = new Paint();
        imgPaint.setAntiAlias(true);
        imgPaint.setShader(mBitmapShader);

        mBitmapWidth = mBitmap.getWidth();
        mBitmapHeight = mBitmap.getHeight();

        mBorderRect.set(0,0,getWidth(),getHeight());
        Log.i(TAG, "init: getWidth:"+getWidth()+";******getHeight:"+getHeight());
        mBorderRadius = (int) (Math.min(mBorderRect.height() - mBorderWidth,mBorderRect.width() - mBorderWidth)/2);

        mDrawableRect.set(mBorderWidth,mBorderWidth,mBorderRect.width() - mBorderWidth,mBorderRect.height() - mBorderWidth);
        mDrawableRadius = (int) (Math.min(mDrawableRect.height(),mDrawableRect.width())/2);

        updateShaderMatrix();
        invalidate();

    }

    private void updateShaderMatrix(){
        Log.i(TAG, "updateShaderMatrix: ");
        float scale;
        float dx = 0;
        float dy = 0;

        mShaderMatrix.set(null);

        if (mDrawableRect.width() * mBitmapHeight > mDrawableRect.height() * mBitmapWidth){
            scale = mDrawableRect.width() / mBitmapWidth;
            dx = (mDrawableRect.width() - mBitmapWidth * scale) / 2;
        }else{
            scale = mDrawableRect.height() / mBitmapHeight;
            dy = (mDrawableRect.height() - mBitmapHeight * scale ) / 2;
        }

        mShaderMatrix.setScale(scale,scale);
        mShaderMatrix.postTranslate(mBorderWidth + (int) (dx + 0.5f),mBorderWidth + (int)(dy + 0.5f));
        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = measureWidth(widthMeasureSpec);
        int measureHeight = measureHeight(heightMeasureSpec);
        Log.i(TAG, "onMeasure: \nmeasureWidth:"+measureWidth+"\nmeasureHeight:"+measureHeight);
        setMeasuredDimension(measureWidth,measureHeight);
        mCenterX = measureWidth/2;
        mCenterY = measureHeight/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(TAG, "onDraw: ");
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: "+getWidth()+"????????"+getHeight());
//        if (getDrawable() == null)
//            return;
        canvas.drawCircle(getWidth()/2,getHeight()/2,mDrawableRadius,mPaint);
        canvas.drawCircle(getWidth()/2,getHeight()/2,mBorderRadius,imgPaint);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG, "onSizeChanged: ");
        init();
    }

    private int measureWidth(int widthMeasureSpec){
        int result = 400;

        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);

        switch (mode){
            case MeasureSpec.EXACTLY://match_parent or exactly number
                result = size;

                break;
            case MeasureSpec.AT_MOST://wrap content
                result = Math.min(result,size);

                break;
            default:
                break;
        }
        return result;
    }

    private int measureHeight(int heightMeasureSpec){
        int result = 400;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        switch (mode){
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(result,size);
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
    }
}
