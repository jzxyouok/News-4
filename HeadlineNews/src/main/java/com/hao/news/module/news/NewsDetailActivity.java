package com.hao.news.module.news;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.hao.news.R;
import com.hao.news.model.NewsBean;
import com.hao.news.utils.ImageLoaderUtils;
import com.hao.news.utils.ToolsUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class NewsDetailActivity extends SwipeBackActivity {

    @Bind(R.id.ivImage)
    ImageView ivImage;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    private SwipeBackLayout mSwipeBackLayout;

    NewsBean.ResultBean.DataBean dataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);

        //设置可以支持toobar
        setSupportActionBar(toolbar);
//        给左上角的左边添加一个返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //滑动返回
        mSwipeBackLayout = getSwipeBackLayout();
        //设置滑动距离,及滑动方向
        mSwipeBackLayout.setEdgeSize(ToolsUtil.getWidthInPx(this));
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        //获取上一个界面的数据
        dataBean = (NewsBean.ResultBean.DataBean) getIntent().getSerializableExtra("dataBean");
        collapsingToolbar.setTitle(dataBean.getTitle()); //标题
        ImageLoaderUtils.display(this, ivImage, dataBean.getThumbnail_pic_s03()); //图片

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl(dataBean.getUrl());

    }
}
