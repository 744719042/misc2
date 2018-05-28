package com.example.misc2.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class BezierView extends View {
    private Paint mPaint;
    private Point mStart;
    private Point mEnd;
    private Point mCurrent;
    private ValueAnimator mValueAnimator;
    private float mProgress;

    public BezierView(Context context) {
        this(context, null);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(10);

        mStart = new Point();
        mStart.x = 100;
        mStart.y = 100;

        mEnd = new Point();
        mEnd.x = 600;
        mEnd.y = 600;

        mCurrent = new Point();

        mValueAnimator = ValueAnimator.ofFloat(0, 1f);
        mValueAnimator.setDuration(3000);
        mValueAnimator.addUpdateListener(animation -> {
            mProgress = animation.getAnimatedFraction();
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
        mCurrent.x = (int) (mStart.x + mProgress * (mEnd.x - mStart.x));
        mCurrent.y = (int) (mStart.y + mProgress * (mEnd.y - mStart.y));
        mPaint.setColor(Color.RED);
        canvas.drawLine(mStart.x, mStart.y, mCurrent.x, mCurrent.y, mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(mCurrent.x, mCurrent.y, 10, mPaint);
        mPaint.setColor(Color.GREEN);
        canvas.drawLine(mCurrent.x + 8, mCurrent.y + 8, mEnd.x, mEnd.y, mPaint);
    }
}
