package com.example.misc2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.misc2.widget.StrokeTextView;

public class StrokeActivity extends AppCompatActivity {
    private ImageView imageView;
    private StrokeTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stroke);
        imageView = findViewById(R.id.image);
        textView = findViewById(R.id.stroke_text_view);
    }
}
