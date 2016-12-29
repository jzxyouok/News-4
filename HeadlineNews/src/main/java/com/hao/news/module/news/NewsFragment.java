package com.hao.news.module.news;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hao.news.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 新闻
 */
public class NewsFragment extends Fragment {

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private View view;
    private String[] mTitles = {"头条", "社会", "国内", "国际","娱乐", "体育", "军事", "科技", "财经", "时尚"};
    private String[] mType = {"top", "shehui", "guonei", "guoji", "yule", "tiyu", "junshi", "keji", "caijing","shishang"};


    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, view);

        //init tabLayout
        initTabLayout();;
        //数据源
        tabLayout.setupWithViewPager(viewpager);
//        viewpager.setOffscreenPageLimit(1); //指定加载的页数，增加缓冲页面
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager());
        viewpager.setAdapter(fragmentPagerAdapter);
        return view;
    }

    /**
     * 初始化Tablayout
     */
    private void initTabLayout() {
        for (int i = 0; i < mTitles.length; i++) {
            tabLayout.addTab(tabLayout.newTab());
        }
    }

    private class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
        public FragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return NewsListFragment.newInstance(mType[0]);
                case 1:
                    return NewsListFragment.newInstance(mType[1]);
                case 2:
                    return NewsListFragment.newInstance(mType[2]);
                case 3:
                    return NewsListFragment.newInstance(mType[3]);
                case 4:
                    return NewsListFragment.newInstance(mType[4]);
                case 5:
                    return NewsListFragment.newInstance(mType[5]);
                case 6:
                    return NewsListFragment.newInstance(mType[6]);
                case 7:
                    return NewsListFragment.newInstance(mType[7]);
                case 8:
                    return NewsListFragment.newInstance(mType[8]);
            }
            return NewsListFragment.newInstance(mType[0]);
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
