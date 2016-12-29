package com.hao.news;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.hao.news.module.news.NewsFragment;
import com.hao.news.module.pic.PictureFragment;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, NewsFragment.newInstance()).commit();
        }
        initView(savedInstanceState);
    }

    /**
     * 初始化view
     */
    private void initView(Bundle savedInstanceState) {
        //初始化tooBar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, false);
//        setSupportActionBar(toolbar);
        //初始化floatingActionButton
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //初始化DrawerLayout
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        //初始化NavigationView
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //默认新闻
        navigationView.setCheckedItem(R.id.nav_news);
    }

    //后退按键
    public void onBackPressed() {
        DrawerLayout mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        // Handle navigation view item clicks here.
        mDrawer.closeDrawer(GravityCompat.START);
        mDrawer.postDelayed(new Runnable() {
            @Override
            public void run() {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_news: //新闻
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, NewsFragment.newInstance()).commit();
                        toolbar.setTitle("新闻");
                        break;
                    case R.id.nav_pic:  //图片
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, PictureFragment.newInstance()).commit();
                        toolbar.setTitle("图片");
                        break;
                    case R.id.nav_video: //视频
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, NewsFragment.newInstance()).commit();
                        break;
                    case R.id.nav_set: //设置
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, NewsFragment.newInstance()).commit();
                        break;
                    case R.id.nav_about: //关于
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, NewsFragment.newInstance()).commit();
                        break;
                }
            }
        }, 250);
        return true;
    }
}
