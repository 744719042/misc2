package com.example.misc2;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.misc2.utils.ExceptionUtils;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class BugFixActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "BugFixActivity";

    private Button invokeBug;
    private Button invokeFix;
    private Button showLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug_fix);
        invokeBug = findViewById(R.id.invokeError);
        invokeFix = findViewById(R.id.invokeFix);
        showLoader = findViewById(R.id.showLoader);
        invokeBug.setOnClickListener(this);
        invokeFix.setOnClickListener(this);
        showLoader.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == invokeBug) {
            try {
                ExceptionUtils.parseException(null);
            } catch (Exception e) {
                Toast.makeText(this, "调用BUG代码失败", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "调用修复BUG代码成功", Toast.LENGTH_SHORT).show();
        } else if (v == invokeFix) {
            if (loadFixDex()) {
                Toast.makeText(this, "修复BUG成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "修复BUG失败", Toast.LENGTH_SHORT).show();
            }
        } else if (v == showLoader) {
            ClassLoader classLoader = getClassLoader();
            Log.d(TAG, classLoader.toString());
            while (classLoader != null) {
                classLoader = classLoader.getParent();
                if (classLoader != null) {
                    Log.d(TAG, classLoader.toString());
                }
            }
        }
    }

    private boolean loadFixDex() {
        String dexPath = Environment.getExternalStorageDirectory() + File.separator + "bugfix.dex";
        String dexOutput = getCacheDir() + File.separator + "DEX";
        File file = new File(dexOutput);
        if (!file.exists()) file.mkdirs();
        // 从bugfix.dex文件加载修复bug的dex文件
        DexClassLoader dexClassLoader = new DexClassLoader(dexPath, dexOutput, null, getClassLoader());

        PathClassLoader pathClassLoader = (PathClassLoader) getClassLoader();
        try {
            // 反射获取pathList成员变量Field
            Field dexPathList = BaseDexClassLoader.class.getDeclaredField("pathList");
            dexPathList.setAccessible(true);
            // 现获取两个类加载器内部的pathList成员变量
            Object pathList = dexPathList.get(pathClassLoader);
            Object fixPathList = dexPathList.get(dexClassLoader);

            // 反射获取DexPathList类的dexElements成员变量Field
            Field dexElements = pathList.getClass().getDeclaredField("dexElements");
            dexElements.setAccessible(true);
            // 反射获取pathList对象内部的dexElements成员变量
            Object originDexElements = dexElements.get(pathList);
            Object fixDexElements = dexElements.get(fixPathList);

            // 使用反射获取两个dexElements的长度
            int originLength = Array.getLength(originDexElements);
            int fixLength = Array.getLength(fixDexElements);
            int totalLength = originLength + fixLength;
            // 获取dexElements数组的元素类型
            Class<?> componentClass = originDexElements.getClass().getComponentType();
            // 将修复dexElements的元素放在前面，原始dexElements放到后面，这样就保证加载类的时候优先查找修复类
            Object[] elements = (Object[]) Array.newInstance(componentClass, totalLength);
            for (int i = 0; i < totalLength; i++) {
                if (i < fixLength) {
                    elements[i] = Array.get(fixDexElements, i);
                } else {
                    elements[i] = Array.get(originDexElements, i - fixLength);
                }
            }
            // 将新生成的dexElements数组注入到PathClassLoader内部，
            // 这样App查找类就会先从fixdex查找，在从App安装的dex里查找
            dexElements.set(pathList, elements);
            return true;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }
}
