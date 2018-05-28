package com.example.misc2.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import com.example.misc2.R;
import com.example.misc2.utils.CommonUtils;

public class FlightPathView extends AppCompatImageView {
    private Path mPath;
    private Path mTmpPath;
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private ValueAnimator mPathAnimator;
    private Bitmap mFlight;
    private float[] mPos;
    private float[] mTan;
    private Matrix mMatrix;

    public FlightPathView(Context context) {
        this(context, null);
    }

    public FlightPathView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlightPathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath = new Path();
        mTmpPath = new Path();
        mFlight = BitmapFactory.decodeResource(getResources(), R.drawable.flight_ic);
        mPos = new float[2];
        mTan = new float[2];
        mMatrix = new Matrix();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPath.addCircle(w / 2, h / 2, Math.min(w / 2, h / 2) - CommonUtils.dp2px(20), Path.Direction.CCW);

        mPathMeasure = new PathMeasure(mPath, false);
        float length = mPathMeasure.getLength();
        mPathAnimator = ValueAnimator.ofFloat(0f, length);
        mPathAnimator.setDuration(2000);
        mPathAnimator.setInterpolator(new LinearInterpolator());
        mPathAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mPathAnimator.setRepeatMode(ValueAnimator.RESTART);
        mPathAnimator.addUpdateListener(animation -> {
            float current = (float) animation.getAnimatedValue();
            mTmpPath.reset();
            mPathMeasure.getSegment(0f, current, mTmpPath, true);
            mPathMeasure.getPosTan(current, mPos, mTan);
            mMatrix.reset();                                                        // 重置Matrix
            float degrees = (float) (Math.atan2(mTan[1], mTan[0]) * 180.0 / Math.PI); // 计算图片旋转角度

            mMatrix.postRotate((degrees + 45f), mFlight.getWidth() / 2, mFlight.getHeight() / 2);   // 旋转图片
            mMatrix.postTranslate(mPos[0] - mFlight.getWidth() / 2, mPos[1] - mFlight.getHeight() / 2);

            invalidate();
        });
        mPathAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mTmpPath, mPaint);
        canvas.drawBitmap(mFlight, mMatrix, mPaint);
    }
}
