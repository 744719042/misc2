package com.example.misc2.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import com.example.misc2.R;

public class PathView extends AppCompatImageView {
    private Path mRight;
    private Path mTmpPath;
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private ValueAnimator mPathAnimator;

    public PathView(Context context) {
        this(context, null);
    }

    public PathView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mRight = new Path();
        mTmpPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        mPath.addRect(4, 4, w - 4, h - 4, Path.Direction.CCW);
        mRight.moveTo(0, h / 2);
        mRight.lineTo(w / 4, h);
        mRight.lineTo(w, 0);

        mPathMeasure = new PathMeasure(mRight, false);
        float length = mPathMeasure.getLength();
        mPathAnimator = ValueAnimator.ofFloat(0f, length);
        mPathAnimator.setDuration(1000);
        mPathAnimator.setInterpolator(new LinearInterpolator());
        mPathAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mPathAnimator.setRepeatMode(ValueAnimator.RESTART);
        mPathAnimator.addUpdateListener(animation -> {
            float current = (float) animation.getAnimatedValue();
            mTmpPath.reset();
            mPathMeasure.getSegment(0f, current, mTmpPath, true);
            invalidate();
        });
        mPathAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(5);
        canvas.drawRect(4, 4, getMeasuredWidth() - 4, getMeasuredHeight() - 4, mPaint);
        mPaint.setStrokeWidth(10);
        canvas.drawPath(mTmpPath, mPaint);
    }
}
