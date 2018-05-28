package com.example.misc2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.misc2.manager.ResourceManager;

public class DrawerActivity extends AppCompatActivity {
    private TextView contentText;
    private TextView menuText;
    private ImageView contentBg;
    private ImageView menuBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        contentText = findViewById(R.id.content_text);
        menuText = findViewById(R.id.menu_text);
        contentBg = findViewById(R.id.content);
        menuBg = findViewById(R.id.menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.skin_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_change_skin:
                ResourceManager.ResourceEntity entity = ResourceManager.getInstance().getResource(ResourceManager.APK_RESOURCE);
                initViews(entity);
                return true;
            case R.id.action_reset_skin:
                ResourceManager.ResourceEntity defEntity = ResourceManager.getInstance().getResource(ResourceManager.DEFAULT_RESOURCE);
                initViews(defEntity);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews(ResourceManager.ResourceEntity entity) {
        menuBg.setImageDrawable(entity.menuBg);
        contentBg.setImageDrawable(entity.contentBg);
        menuText.setTextColor(entity.menuColor);
        contentText.setTextColor(entity.contentColor);
    }
}
