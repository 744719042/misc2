package com.example.misc2.widget;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.misc2.utils.CommonUtils;

public class CircleDrawable extends Drawable implements Animatable {
    private static final String TAG = "CircleDrawable";
    private Paint paint;
    private ValueAnimator valueAnimator;
    private int mRadius;
    private int width;
    private int height;
    private long mDelay;
    private boolean mIsStarted = false;

    public CircleDrawable() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(CommonUtils.dp2px(2));
        paint.setStyle(Paint.Style.STROKE);
        mRadius = CommonUtils.dp2px(10);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawCircle(width / 2, height / 2, mRadius, paint);
    }

    public void setStartDelay(long delay) {
        mDelay = delay;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        width = bounds.width();
        height = bounds.height();
        Log.d(TAG, "width = " + width + ", height = " + height);
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }

        valueAnimator = ValueAnimator.ofInt(CommonUtils.dp2px(10), Math.min(width, height) / 2);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRadius = (int) animation.getAnimatedValue();
                Log.d(TAG, "width = " + width + ", height = " + height);
                Log.d(TAG, "mRadius = " + mRadius);
                invalidateSelf();
            }
        });
        valueAnimator.setStartDelay(mDelay);
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);

        if (mIsStarted) {
            valueAnimator.start();
        }
    }

    @Override
    public void start() {
        if (!mIsStarted) {
            if (valueAnimator != null) {
                valueAnimator.start();
            }
            mIsStarted = true;
        }
    }

    @Override
    public void stop() {
        if (mIsStarted) {
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            mIsStarted = false;
        }
    }

    @Override
    public boolean isRunning() {
        return valueAnimator.isRunning();
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}
