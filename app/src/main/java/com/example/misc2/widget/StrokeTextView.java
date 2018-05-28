package com.example.misc2.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;

import com.example.misc2.R;

public class StrokeTextView extends AppCompatTextView {
    private int mStrokeColor = Color.WHITE;
    private float mStrokeWidth = 0;
    private AppCompatTextView mStrokeView;

    public StrokeTextView(Context context) {
        this(context, null);
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StrokeTextView);
            mStrokeColor = a.getColor(R.styleable.StrokeTextView_strokeColor, Color.WHITE);
            mStrokeWidth = a.getDimension(R.styleable.StrokeTextView_strokeWidth, 0f);
            a.recycle();
        }

        mStrokeView = new AppCompatTextView(context);
        mStrokeView.setText(getText());
        mStrokeView.setGravity(getGravity());
        mStrokeView.setTextColor(mStrokeColor);
        mStrokeView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getTextSize());
        mStrokeView.getPaint().setStyle(Paint.Style.STROKE);
        mStrokeView.getPaint().setStrokeWidth(mStrokeWidth);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        super.setLayoutParams(params);
        mStrokeView.setLayoutParams(params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!getText().equals(mStrokeView.getText())) {
            if (!getText().equals(mStrokeView.getText())) {
                mStrokeView.setText(getText());
            }
        }
        mStrokeView.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mStrokeView.layout(left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mStrokeView.draw(canvas);
        super.onDraw(canvas);
    }
}
