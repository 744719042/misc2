package com.example.misc2.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Bezier2View extends View {
    private Paint mPaint;
    private Point mStart;
    private Point mControl;
    private Point mEnd;
    private Point mCurrent1;
    private Point mCurrent2;
    private Path mPath;
    private Point mLastPoint;
    private Point mTmpPoint;
    private ValueAnimator mValueAnimator;
    private float mProgress;

    public Bezier2View(Context context) {
        this(context, null);
    }

    public Bezier2View(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Bezier2View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);

        mStart = new Point();
        mStart.x = 100;
        mStart.y = 600;

        mLastPoint = new Point();
        mLastPoint.x = mStart.x;
        mLastPoint.y = mStart.y;

        mControl = new Point();
        mControl.x = 350;
        mControl.y = 250;

        mEnd = new Point();
        mEnd.x = 600;
        mEnd.y = 600;

        mCurrent1 = new Point();
        mCurrent2 = new Point();
        mTmpPoint = new Point();

        mPath = new Path();

        mValueAnimator = ValueAnimator.ofFloat(0, 1f);
        mValueAnimator.setDuration(3000);
        mValueAnimator.addUpdateListener(animation -> {
            mProgress = animation.getAnimatedFraction();
            if (mProgress <= 0.0001f || mProgress >= 0.9999f) {
                mPath.reset();
                mLastPoint.x = mStart.x;
                mLastPoint.y = mStart.y;
            }
            invalidate();
        });
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        post(() -> {
           mValueAnimator.start();
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCurrent1.x = (int) (mStart.x + mProgress * (mControl.x - mStart.x));
        mCurrent1.y = (int) (mStart.y + mProgress * (mControl.y - mStart.y));
        mPaint.setColor(Color.GRAY);
        canvas.drawLine(mStart.x, mStart.y, mCurrent1.x, mCurrent1.y, mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(mCurrent1.x, mCurrent1.y, 5, mPaint);
        mPaint.setColor(Color.GRAY);
        canvas.drawLine(mCurrent1.x + 4, mCurrent1.y - 4, mControl.x, mControl.y, mPaint);

        mCurrent2.x = (int) (mControl.x + mProgress * (mEnd.x - mControl.x));
        mCurrent2.y = (int) (mControl.y + mProgress * (mEnd.y - mControl.y));
        mPaint.setColor(Color.GRAY);
        canvas.drawLine(mControl.x, mControl.y, mCurrent2.x, mCurrent2.y, mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(mCurrent2.x, mCurrent2.y, 5, mPaint);
        mPaint.setColor(Color.GRAY);
        canvas.drawLine(mCurrent2.x + 8, mCurrent2.y + 8, mEnd.x, mEnd.y, mPaint);

        mPaint.setColor(Color.CYAN);
        canvas.drawLine(mCurrent1.x + 8, mCurrent1.y + 8, mCurrent2.x - 8, mCurrent2.y - 8, mPaint);
        mTmpPoint.x = (int) (mCurrent1.x + mProgress * (mCurrent2.x - mCurrent1.x));
        mTmpPoint.y = (int) (mCurrent1.y + mProgress * (mCurrent2.y - mCurrent1.y));
        mPath.moveTo(mLastPoint.x, mLastPoint.y);
        mPath.lineTo(mTmpPoint.x, mTmpPoint.y);
        mLastPoint.x = mTmpPoint.x;
        mLastPoint.y = mTmpPoint.y;
        mPaint.setColor(Color.MAGENTA);
        canvas.drawPath(mPath, mPaint);
    }
}
