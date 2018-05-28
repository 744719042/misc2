package com.example.misc2;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class LoadClassActivity extends AppCompatActivity implements View.OnClickListener {

    private Button getHello;
    private Button factorial;
    private TextView mText;
    private Class<?> clazz;
    private static final String APK_HELLO_CLASS_PATH = "com.example.apkresource.HelloWorld";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_class);
        getHello = findViewById(R.id.getHelloStr);
        factorial = findViewById(R.id.invoke);
        mText = findViewById(R.id.text);

        getHello.setOnClickListener(this);
        factorial.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == getHello) {
            if (clazz == null) {
                loadClass();
            }

            if (clazz != null) {
                try {
                    Object object = clazz.newInstance();
                    Method method = clazz.getMethod("getMessage");
                    String text = (String) method.invoke(object);
                    mText.setText(text);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } else if (v == factorial) {
            if (clazz == null) {
                loadClass();
            }

            if (clazz != null) {
                try {
                    Object object = clazz.newInstance();
                    Method method = clazz.getMethod("factorial", int.class);
                    int value = (int) method.invoke(object, 6);
                    mText.setText(String.valueOf(value));
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadClass() {
        String apkPath = Environment.getExternalStorageDirectory() + File.separator + "appres.apk";
        String dexOutput = getCacheDir() + File.separator + "DEX";
        File file = new File(dexOutput);
        if (!file.exists()) file.mkdirs();
        DexClassLoader dexClassLoader = new DexClassLoader(apkPath, dexOutput, null, getClassLoader());
        try {
            clazz = dexClassLoader.loadClass(APK_HELLO_CLASS_PATH);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
