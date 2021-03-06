package com.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acg12.lib.ui.adapter.CommonRecyclerAdapter;
import com.acg12.ui.adapter.view.HomeTagViewHolder;

import com.acg12.R;

import java.util.List;

/**
 * Created by Administrator on 2018/1/16.
 */

public class HomeTagAdapter extends CommonRecyclerAdapter {

    public HomeTagAdapter(Context mContext) {
        super(mContext);
    }

    public HomeTagAdapter(Context mContext, List<Object> mList) {
        super(mContext, mList);
    }

    @Override
    public RecyclerView.ViewHolder createView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_tag, parent, false);
        return new HomeTagViewHolder(view);
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder, int position) {
        ((HomeTagViewHolder) holder).bindData(mContext , getList() , position);
    }
}
