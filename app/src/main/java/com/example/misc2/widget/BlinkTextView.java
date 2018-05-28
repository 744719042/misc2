package com.example.misc2.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.example.misc2.R;
import com.example.misc2.utils.CommonUtils;

public class BlinkTextView extends AppCompatTextView {
    private Paint mPaint;
    private LinearGradient mGradient;
    private Matrix mMatrix;
    private String mText = "Hello World, Good Morning to you!!";
    private ValueAnimator mValueAnimator;
    public BlinkTextView(Context context) {
        this(context, null);
    }

    public BlinkTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BlinkTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.BLACK);
        setText(mText);
        mPaint = getPaint();
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        mGradient = new LinearGradient(-CommonUtils.dp2px(40), 0, 0, 0,
                new int[] { 0x33ffffff, 0xffffffff, 0x33ffffff },
                new float[] { 0.1f, 0.5f, 0.9f }, Shader.TileMode.CLAMP);
        mMatrix = new Matrix();
        mGradient.setLocalMatrix(mMatrix);
        mPaint.setTextSize(50);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setShader(mGradient);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float width = mPaint.measureText(mText);
        mValueAnimator = ValueAnimator.ofFloat(0, width);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.addUpdateListener(animation ->  {
            float current = (float) animation.getAnimatedValue();
            mMatrix.setTranslate(current, 0);
            mGradient.setLocalMatrix(mMatrix);
            invalidate();
        });
        mValueAnimator.start();
    }
}
