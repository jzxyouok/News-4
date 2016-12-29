package com.hao.news.module.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hao.news.R;
import com.hao.news.listener.OnItemClickListener;
import com.hao.news.model.NewsBean;
import com.hao.news.network.Api;
import com.hao.news.network.HttpUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 新闻子页面
 */
public class NewsListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String ARG_TYPE = "type";
    private String mType;
    public static final int NEWS_TYPE_TOP = 0;
    public static final int NEWS_TYPE_NBA = 1;
    public static final int NEWS_TYPE_CARS = 2;
    public static final int NEWS_TYPE_JOKES = 3;

    private View view;
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    SwipeRefreshLayout swipeRefreshLayout;
    private MyRecycleAdapter myRecycleAdapter;

    public static NewsListFragment newInstance(String type) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(ARG_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news_list, container, false);
        ButterKnife.bind(this, view);

        // init swipeRefreshLayout
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        //初始化recycleView
        //setHasFixedSize()方法用来使RecycleView保持固定的大小
        recycleView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycleView.setLayoutManager(linearLayoutManager);
        myRecycleAdapter = new MyRecycleAdapter();
        recycleView.setAdapter(myRecycleAdapter);
        //设置数据

        initData();


        myRecycleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position, View view) {
                NewsBean.ResultBean.DataBean dataBean = myRecycleAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                intent.putExtra("dataBean", dataBean);
                View transitionView = view.findViewById(R.id.ivNews);

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(), transitionView, getString(R.string.transition_news_img));
                //开启动画
                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
            }
        });
        return this.view;
    }

    @Override
    public void onRefresh() {
        initData();
    }

    /**
     * recycle
     */
    class MyRecycleAdapter extends RecyclerView.Adapter<MyRecycleAdapter.ItemViewHolder> {
        List<NewsBean.ResultBean.DataBean> dataLists = new ArrayList<>();
        OnItemClickListener onItemClickListener;

        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item, parent, false);
            //占满整个屏幕
//            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            final ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(itemViewHolder, itemViewHolder.getAdapterPosition(), view);
                    }
                }
            });
            return itemViewHolder;
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            NewsBean.ResultBean.DataBean dataBean = dataLists.get(position);
            Log.d("onBindViewHolder=", "onBindViewHolder:" + dataBean.toString());
            holder.tvTitle.setText(dataBean.getTitle());  //标题
            holder.tvDes.setText(dataBean.getAuthor_name() + "---" + dataBean.getDate());
            Glide.with(getActivity()).load(dataBean.getThumbnail_pic_s()).placeholder(R.mipmap.icon_pic)
                    .error(R.mipmap.icon_pic).crossFade().into(holder.ivNews);
        }

        @Override
        public int getItemCount() {
            return dataLists.size();
        }

        //设置数据
        public void setData(NewsBean newsBean) {
//            dataLists.clear();
            dataLists.addAll(newsBean.getResult().getData());
            notifyDataSetChanged();
        }

        //设置条目点击监听
        private void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        //获取单个条目
        public NewsBean.ResultBean.DataBean getItem(int position) {
            return dataLists.get(position);
        }

        /**
         * recycleView的viewholder
         */
        class ItemViewHolder extends RecyclerView.ViewHolder {
            ImageView ivNews;
            TextView tvTitle;
            TextView tvDes;

            public ItemViewHolder(View itemView) {
                super(itemView);
                ivNews = (ImageView) itemView.findViewById(R.id.ivNews);
                tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                tvDes = (TextView) itemView.findViewById(R.id.tvDes);
            }
        }

    }

    /**
     * 初始化数据
     */
    private void initData() {
        swipeRefreshLayout.setRefreshing(true);

        HttpUtils.getNews()
                .getNews(mType, Api.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsBean>() {
                    @Override
                    public void onCompleted() {
                        Log.d("onCompleted", "onCompleted");
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e, e.getMessage());
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(NewsBean newsBean) {
                        Log.d("onNext", "onNext");
                        myRecycleAdapter.setData(newsBean);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
