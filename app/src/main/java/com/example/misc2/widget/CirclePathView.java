package com.example.misc2.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import com.example.misc2.utils.CommonUtils;

public class CirclePathView extends AppCompatImageView {
    private Path mPath;
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private ValueAnimator mPathAnimator;
    private ValueAnimator mRightAnimator;
    private Path mTmpPath;
    private Path mRightPath;

    public CirclePathView(Context context) {
        this(context, null);
    }

    public CirclePathView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirclePathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath = new Path();
        mTmpPath = new Path();
        mRightPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float padding = CommonUtils.dp2px(4);
        float rightPadding = padding + CommonUtils.dp2px(2);
        mPath.addCircle(w / 2, h / 2, Math.min(w / 2, h / 2) - padding, Path.Direction.CCW);
        mPath.moveTo(rightPadding, h / 2);
        mPath.lineTo(w / 3, h - rightPadding);
        mPath.lineTo(w - rightPadding, rightPadding);

        mPathMeasure = new PathMeasure(mPath, false);
        float length = mPathMeasure.getLength();
        mPathAnimator = ValueAnimator.ofFloat(0f, length);
        mPathAnimator.setDuration(1000);
        mPathAnimator.setInterpolator(new LinearInterpolator());
        mPathAnimator.addUpdateListener(animation -> {
            float current = (float) animation.getAnimatedValue();
            mTmpPath.reset();
            mPathMeasure.getSegment(0f, current, mTmpPath, true);
            invalidate();
        });
        mPathAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mPathMeasure.nextContour();
                float length = mPathMeasure.getLength();
                mRightAnimator = ValueAnimator.ofFloat(0f, length);
                mRightAnimator.setDuration(1000);
                mRightAnimator.setInterpolator(new LinearInterpolator());
                mRightAnimator.addUpdateListener(anim -> {
                    float current = (float) anim.getAnimatedValue();
                    mRightPath.reset();
                    mPathMeasure.getSegment(0f, current, mRightPath, true);
                    invalidate();
                });
                mRightAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mRightPath.reset();
                        mPathMeasure.setPath(mPath, false);
                        mPathAnimator.start();
                    }
                });
                mRightAnimator.start();
            }
        });
        mPathAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mTmpPath, mPaint);
        canvas.drawPath(mRightPath, mPaint);
    }
}
