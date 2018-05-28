package com.example.misc2.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MultiCircleDrawable extends Drawable implements Animatable, Drawable.Callback {
    private final int START_DELAY = 200;

    private CircleDrawable[] circleDrawables = new CircleDrawable[] {
        new CircleDrawable(),
        new CircleDrawable(),
        new CircleDrawable()
    };

    public MultiCircleDrawable() {
        for (int i = 0; i < circleDrawables.length; i++) {
            circleDrawables[i].setStartDelay(i * START_DELAY);
            circleDrawables[i].setCallback(this);
        }
    }

    @Override
    public void start() {
        for (CircleDrawable drawable : circleDrawables) {
            drawable.start();
        }
    }

    @Override
    public void stop() {
        for (CircleDrawable drawable : circleDrawables) {
            drawable.stop();
        }
    }

    @Override
    public boolean isRunning() {
        for (CircleDrawable drawable : circleDrawables) {
            if (drawable.isRunning()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        for (CircleDrawable drawable : circleDrawables) {
            drawable.draw(canvas);
        }
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        for (CircleDrawable drawable : circleDrawables) {
            drawable.onBoundsChange(bounds);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        for (CircleDrawable drawable : circleDrawables) {
            drawable.setAlpha(alpha);
        }
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        for (CircleDrawable drawable : circleDrawables) {
            drawable.setColorFilter(colorFilter);
        }
    }

    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }

    @Override
    public void invalidateDrawable(@NonNull Drawable who) {
        invalidateSelf();
    }

    @Override
    public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {

    }

    @Override
    public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {

    }
}
