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

public class CustomBezier3View extends View {
    private Point mStart;
    private Point mControl1;
    private Point mControl2;
    private Point mEnd;
    private Paint mPaint;
    private Path mPath;

    public CustomBezier3View(Context context) {
        this(context, null);
    }

    public CustomBezier3View(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomBezier3View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        mControl1 = new Point();
        mControl1.x = 350;
        mControl1.y = 250;

        mControl2 = new Point();
        mControl2.x = 550;
        mControl2.y = 230;

        mEnd = new Point();
        mEnd.x = 800;
        mEnd.y = 600;

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(mStart.x, mStart.y, 5, mPaint);
        canvas.drawCircle(mControl1.x, mControl1.y, 5, mPaint);
        canvas.drawCircle(mControl2.x, mControl2.y, 5, mPaint);
        canvas.drawCircle(mEnd.x, mEnd.y, 5, mPaint);

        mPaint.setColor(Color.RED);
        mPath.moveTo(mStart.x, mStart.y);
        mPath.cubicTo(mControl1.x, mControl1.y, mControl2.x, mControl2.y, mEnd.x, mEnd.y);
        canvas.drawPath(mPath, mPaint);
    }
}
