package com.example.misc2.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.example.misc2.R;

public class StrokeTextView1 extends AppCompatTextView {
    private int mStrokeColor = Color.WHITE;
    private float mStrokeWidth = 0;

    public StrokeTextView1(Context context) {
        this(context, null);
    }

    public StrokeTextView1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StrokeTextView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StrokeTextView);
            mStrokeColor = a.getColor(R.styleable.StrokeTextView_strokeColor, Color.WHITE);
            mStrokeWidth = a.getDimension(R.styleable.StrokeTextView_strokeWidth, 0f);
            a.recycle();
        }

        setTextColor(mStrokeColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        TextPaint paint = getPaint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mStrokeWidth);
        super.onDraw(canvas);
    }
}
