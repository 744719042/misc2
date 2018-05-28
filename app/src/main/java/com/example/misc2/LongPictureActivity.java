package com.example.misc2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.misc2.widget.LargeImageView;

public class LongPictureActivity extends AppCompatActivity {
    private LargeImageView imageView;
    private Button gotoHead;
    private Button gotoTail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_picture);
        imageView = findViewById(R.id.largePicture);
        gotoHead = findViewById(R.id.gotoHead);
        gotoHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.gotoHead();
            }
        });
        gotoTail = findViewById(R.id.gotoTail);
        gotoTail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.gotoTail();
            }
        });
    }
}
