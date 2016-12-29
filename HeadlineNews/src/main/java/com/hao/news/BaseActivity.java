package com.hao.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 初始化toolbar
     */
    public void initToolBar(final Toolbar toolbar, boolean b) {
        toolbar.setTitle("新闻");
        toolbar.inflateMenu(R.menu.main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                final int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.action_settings: //设置
                        Toast.makeText(BaseActivity.this, "设置", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }
}
