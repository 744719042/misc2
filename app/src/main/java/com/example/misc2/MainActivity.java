package com.example.misc2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_WRITE_STORAGE = 0x100;
    private Button drawable;
    private Button ripple;
    private Button stroke;
    private Button largePicture;
    private Button drawer;
    private Button meiyin;
    private Button path;
    private Button text;
    private Button loadClass;
    private Button fixBug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawable = findViewById(R.id.drawable);
        drawable.setOnClickListener(this);
        ripple = findViewById(R.id.ripple);
        ripple.setOnClickListener(this);
        stroke = findViewById(R.id.stroke);
        stroke.setOnClickListener(this);
        largePicture = findViewById(R.id.largePicture);
        largePicture.setOnClickListener(this);
        drawer = findViewById(R.id.drawer);
        drawer.setOnClickListener(this);
        meiyin = findViewById(R.id.meiyin);
        meiyin.setOnClickListener(this);
        path = findViewById(R.id.path);
        path.setOnClickListener(this);
        text = findViewById(R.id.text);
        text.setOnClickListener(this);
        loadClass = findViewById(R.id.loadClass);
        loadClass.setOnClickListener(this);
        fixBug = findViewById(R.id.fixBug);
        fixBug.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_STORAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_STORAGE && grantResults.length == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "授权成功", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (drawable == v) {
            Intent intent = new Intent(this, DrawableActivity.class);
            startActivity(intent);
        } else if (v == ripple) {
            Intent intent = new Intent(this, RippleActivity.class);
            startActivity(intent);
        } else if (v == stroke) {
            Intent intent = new Intent(this, StrokeActivity.class);
            startActivity(intent);
        } else if (v == largePicture) {
            Intent intent = new Intent(this, LongPictureActivity.class);
            startActivity(intent);
        } else if (v == drawer) {
            Intent intent = new Intent(this, DrawerActivity.class);
            startActivity(intent);
        } else if (v == meiyin) {
            Intent intent = new Intent(this, BezierActivity.class);
//            intent.setData(Uri.parse("meiyin://direct?page=me"));
            startActivity(intent);
        } else if (v == path) {
            Intent intent = new Intent(this, PathActivity.class);
            startActivity(intent);
        } else if (v == text) {
            Intent intent = new Intent(this, TextAnimatorActivity.class);
            startActivity(intent);
        } else if (v == loadClass) {
            Intent intent = new Intent(this, LoadClassActivity.class);
            startActivity(intent);
        } else if (v == fixBug) {
            Intent intent = new Intent(this, BugFixActivity.class);
            startActivity(intent);
        }
    }
}
