package com.example.misc2.manager;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.example.misc2.Application;
import com.example.misc2.R;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    public static final String DEFAULT_RESOURCE = "default_resource";
    public static final String APK_RESOURCE = "apk_resource";

    private Map<String, ResourceEntity> mCacheMap = new HashMap<>();

    private ResourceManager() {
        ResourceEntity defaultEntity = new ResourceEntity();
        Resources resources = Application.getContext().getResources();
        defaultEntity.contentBg = resources.getDrawable(R.drawable.skin_content_bg);
        defaultEntity.menuBg = resources.getDrawable(R.drawable.skin_menu_bg);
        defaultEntity.contentColor = resources.getColor(R.color.skin_red);
        defaultEntity.menuColor = resources.getColor(R.color.skin_orange);
        mCacheMap.put(DEFAULT_RESOURCE, defaultEntity);
    }

    private static class ResourceManagerHolder {
        private static final ResourceManager INSTANCE = new ResourceManager();
    }

    public static ResourceManager getInstance() {
        return ResourceManagerHolder.INSTANCE;
    }

    public void add(String key, ResourceEntity entity) {
        mCacheMap.put(key, entity);
    }

    public ResourceEntity getResource(String key) {
        ResourceEntity entity = mCacheMap.get(key);
        if (entity == null) {
            if (APK_RESOURCE.equals(key)) {
                entity = loadResources();
                mCacheMap.put(key, entity);
                return entity;
            } else {
                return mCacheMap.get(DEFAULT_RESOURCE);
            }
        } else {
            return entity;
        }
    }

    private ResourceEntity loadResources() {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = AssetManager.class.getMethod("addAssetPath", String.class);
            String path = Environment.getExternalStorageDirectory() + File.separator + "appres.apk";
            method.invoke(assetManager, path);

            Resources originResources = Application.getContext().getResources();
            String pkgName = "com.example.apkresource";
            Resources resources = new Resources(assetManager, originResources.getDisplayMetrics(), originResources.getConfiguration());
            int contentBgId = resources.getIdentifier("content_bg", "drawable", pkgName);
            int menuBgId = resources.getIdentifier("menu_bg", "drawable", pkgName);
            int contentColorId = resources.getIdentifier("content_text_color", "color", pkgName);
            int menuColorId = resources.getIdentifier("menu_text_color", "color", pkgName);

            ResourceEntity entity = new ResourceEntity();
            entity.menuBg = resources.getDrawable(menuBgId);
            entity.contentBg = resources.getDrawable(contentBgId);
            entity.menuColor = resources.getColor(menuColorId);
            entity.contentColor = resources.getColor(contentColorId);

            return entity;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class ResourceEntity {
        public Drawable contentBg;
        public Drawable menuBg;
        public int contentColor;
        public int menuColor;
    }
}
