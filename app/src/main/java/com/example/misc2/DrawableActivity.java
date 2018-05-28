package com.example.misc2;

import android.graphics.drawable.Animatable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.misc2.widget.CircleDrawable;
import com.example.misc2.widget.MultiCircleDrawable;

public class DrawableActivity extends AppCompatActivity {
    private ImageView imageView;
    private ImageView imageView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);
        imageView = findViewById(R.id.image);
        imageView.setImageDrawable(new CircleDrawable());
        Animatable animatable = (Animatable) imageView.getDrawable();
        animatable.start();

        imageView2 = findViewById(R.id.image2);
        imageView2.setImageDrawable(new MultiCircleDrawable());
        Animatable multiAnimate = (Animatable) imageView2.getDrawable();
        multiAnimate.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Animatable animatable = (Animatable) imageView.getDrawable();
        animatable.stop();

        Animatable multiAnimate = (Animatable) imageView2.getDrawable();
        multiAnimate.stop();
    }
}
