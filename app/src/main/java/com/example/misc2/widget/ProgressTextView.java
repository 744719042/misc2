package com.example.misc2.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.misc2.R;
import com.example.misc2.utils.CommonUtils;

public class ProgressTextView extends View {
    private Paint mPaint;
    private String mText = "中华人民共和国成立啦！！";
    private ValueAnimator mValueAnimator;
    private Rect mRect = new Rect();

    public ProgressTextView(Context context) {
        this(context, null);
    }

    public ProgressTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setTextSize(60);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRect.top = 0;
        mRect.left = 0;
        int textWidth = (int) mPaint.measureText(mText);
        mRect.right = textWidth;
        mRect.bottom = h;
        mValueAnimator = ValueAnimator.ofFloat(0, 1f);
        mValueAnimator.setDuration(5000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.addUpdateListener(animation ->  {
            float progress = (float) animation.getAnimatedValue();
            mRect.right = (int) (textWidth * progress);
            invalidate();
        });
        mValueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        canvas.drawText(mText, 0, 60, mPaint);
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        canvas.save();
        canvas.clipRect(mRect);
        canvas.drawText(mText, 0, 60, mPaint);
        canvas.restore();
    }
}
