package com.hao.news.listener;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2016/12/27.
 */

public interface OnItemClickListener {

    void onItemClick(RecyclerView.ViewHolder holder, int position, View view);
}
