package com.example.misc2.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import java.io.IOException;
import java.io.InputStream;

public class LargeImageView extends AppCompatImageView {
    private int mPictureWidth;
    private int mPictureHeight;
    private int mViewWidth;
    private int mViewHeight;
    private int mShowWidth;
    private int mShowHeight;
    private int mPortX = 0;
    private int mPortY = 0;
    private Bitmap mBitmap;
    private BitmapRegionDecoder mDecoder;
    private Rect mRect;
    private BitmapFactory.Options mOptions;
    private Paint mPaint;

    public LargeImageView(Context context) {
        this(context, null);
    }

    public LargeImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LargeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        try {
            InputStream inputStream = context.getAssets().open("long_picture.jpg");
            mDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);
            mPictureWidth = options.outWidth;
            mPictureHeight = options.outHeight;
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRect = new Rect();
        mOptions = new BitmapFactory.Options();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;

        mShowWidth = mPictureWidth > mViewWidth ? mViewWidth : mPictureWidth;
        mShowHeight = mPictureHeight > mViewHeight ? mViewHeight : mPictureHeight;

        mPortX = (mPictureWidth - mShowWidth) / 2;
        mPortY = (mPictureHeight - mShowHeight) / 2;

        mBitmap = Bitmap.createBitmap(mShowWidth, mShowHeight, Bitmap.Config.ARGB_8888);
        loadPartial();
    }

    private void loadPartial() {
        mRect.set(mPortX, mPortY, mPortX + mShowWidth, mPortY + mShowHeight);
        mBitmap = mDecoder.decodeRegion(mRect, mOptions);
    }

    private int mLastX;
    private int mLastY;
    private int mMotionX;
    private int mMotionY;
    private boolean mIsDragging = false;
    private int mTouchSlop;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX(), y = (int) event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = mMotionX = x;
                mLastY = mMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mIsDragging && isDragging(x - mMotionX, y - mMotionY)) {
                    mIsDragging = true;
                }

                if (mIsDragging) {
                    int dx = x - mLastX;
                    int dy = y - mLastY;
                    boolean changed = false;
                    if (canHorizontalMove() && mPortX - dx >= 0 && mPortX - dx <= mPictureWidth - mShowWidth) {
                        mPortX -= dx;
                        changed = true;
                    }

                    if (canVerticalMove() && mPortY - dy >= 0 && mPortY - dy <= mPictureHeight - mShowHeight) {
                        mPortY -= dy;
                        changed = true;
                    }

                    if (changed) {
                        loadPartial();
                        postInvalidate();
                    }
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mIsDragging = false;
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
    }

    public void gotoHead() {
        mPortX = 0;
        mPortY = 0;
        loadPartial();
        invalidate();
    }

    public void gotoTail() {
        mPortX = mPictureWidth - mShowWidth;
        mPortY = 0;
        loadPartial();
        invalidate();
    }

    private boolean isDragging(int dx, int dy) {
        return dx * dx + dy * dy > mTouchSlop * mTouchSlop;
    }

    private boolean canVerticalMove() {
        return mPictureHeight > mViewHeight;
    }

    private boolean canHorizontalMove() {
        return mPictureWidth > mViewWidth;
    }
}
