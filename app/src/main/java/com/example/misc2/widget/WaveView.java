package com.example.misc2.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.misc2.utils.CommonUtils;

public class WaveView extends View {
    private int mVisibleWaveCount = 2;
    private int mHeight = CommonUtils.dp2px(300);
    private int mOffset = 0;
    private int mWaveLength;
    private int mWaveHeight;
    private Paint mPaint;
    private Path mPath;
    private ValueAnimator mValueAnimator;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.CYAN);
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWaveLength = w / mVisibleWaveCount;
        mWaveHeight = CommonUtils.dp2px(20);
        mValueAnimator = ValueAnimator.ofInt(-w, 0);
        mValueAnimator.setDuration(1500);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener( animation -> {
            mOffset = (int) animation.getAnimatedValue();
            invalidate();
        });
        mValueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int drawWaveCount = 2 * mVisibleWaveCount;
        mPath.reset();
        for (int i = 0; i < drawWaveCount; i++) {
            drawWave(mOffset + i * mWaveLength, mHeight);
        }
        canvas.drawPath(mPath, mPaint);
    }

    private void drawWave(int x, int y) {
        int halfLength = mWaveLength / 2;
        int controlHeight = mWaveHeight + CommonUtils.dp2px(10);
        mPath.moveTo(x, y);
        mPath.rQuadTo(halfLength / 2, -controlHeight, halfLength, 0);
        mPath.rQuadTo(halfLength / 2, controlHeight, halfLength, 0);
        mPath.rLineTo(0, mHeight);
        mPath.rLineTo(-mWaveLength, 0);
        mPath.close();
    }
}
