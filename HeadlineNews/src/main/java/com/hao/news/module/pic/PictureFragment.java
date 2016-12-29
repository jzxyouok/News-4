package com.hao.news.module.pic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hao.news.R;
import com.hao.news.listener.OnItemClickListener;
import com.hao.news.model.ItemModel;
import com.hao.news.network.HttpUtils;
import com.hao.news.utils.GankBeautyResultToItemsMapper;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 图片
 */
public class PictureFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    SwipeRefreshLayout swipeRefreshLayout;
    private View view;
    private MRecycleViewAdapter mRecycleViewAdapter;

    public static PictureFragment newInstance() {
        PictureFragment fragment = new PictureFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pic, container, false);
        ButterKnife.bind(this, view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        initView();
        initData();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        swipeRefreshLayout.setRefreshing(true);
        HttpUtils.getGankPic()
                .getGankPic(100, 1)
                .map(GankBeautyResultToItemsMapper.getInstance())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ItemModel>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("onCompleted=", "onCompleted");
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e, e.getMessage());
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<ItemModel> itemModels) {
                        Log.d("onNext=", "itemModels=" + itemModels);
                        mRecycleViewAdapter.setDatas(itemModels);
                    }
                });
    }

    /**
     * 初始化view
     */
    private void initView() {
        //init swipeRefreshLayout
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                R.color.colorPrimaryDark, R.color.colorAccent,
                R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);
        //init recycleView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setHasFixedSize(true);
        mRecycleViewAdapter = new MRecycleViewAdapter();
        recycleView.setAdapter(mRecycleViewAdapter);
    }

    class MRecycleViewAdapter extends RecyclerView.Adapter<MRecycleViewAdapter.ItemViewHolder> {
        List<ItemModel> itemModelList = new ArrayList<>();
        private OnItemClickListener onItemClickListener;

        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.recycleview_item_pic, parent, false);

            final ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            return itemViewHolder;
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, final int position) {
            ItemModel itemModel = itemModelList.get(position);
            holder.simpleDraweeView.setImageURI(itemModel.imageUrl);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ViewPagerActivity.class);
                    intent.putExtra("position", position);
                    intent.putParcelableArrayListExtra("picList", (ArrayList<? extends Parcelable>) itemModelList);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return itemModelList.size();
        }

        //设置数据
        public void setDatas(List<ItemModel> itemModels) {
            this.itemModelList.clear();
            this.itemModelList.addAll(itemModels);
            notifyDataSetChanged();
        }

        //设置单个条目点击监听
        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {
            SimpleDraweeView simpleDraweeView;

            public ItemViewHolder(View itemView) {
                super(itemView);
                simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.simpleDraweeView);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 刷新
     */
    public void onRefresh() {
        initData();
    }
}
