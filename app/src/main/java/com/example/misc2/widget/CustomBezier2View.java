package com.example.misc2.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CustomBezier2View extends View {
    private Point mStart;
    private Point mControl;
    private Point mEnd;
    private Paint mPaint;
    private Path mPath;

    public CustomBezier2View(Context context) {
        this(context, null);
    }

    public CustomBezier2View(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomBezier2View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);

        mStart = new Point();
        mStart.x = 100;
        mStart.y = 600;

        mControl = new Point();
        mControl.x = 350;
        mControl.y = 250;

        mEnd = new Point();
        mEnd.x = 600;
        mEnd.y = 600;

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(mStart.x, mStart.y, 5, mPaint);
        canvas.drawCircle(mControl.x, mControl.y, 5, mPaint);
        canvas.drawCircle(mEnd.x, mEnd.y, 5, mPaint);
        mPaint.setColor(Color.RED);
        mPath.moveTo(mStart.x, mStart.y);
        mPath.quadTo(mControl.x, mControl.y, mEnd.x, mEnd.y);
        canvas.drawPath(mPath, mPaint);
    }
}
