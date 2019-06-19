package com.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acg12.lib.ui.adapter.CommonRecyclerAdapter;
import com.acg12.ui.adapter.view.SearchViewHolder;

import com.acg12.R;

import java.util.List;

/**
 * Created by Administrator on 2018/1/18.
 */

public class SearchAdapter extends CommonRecyclerAdapter {

    public SearchAdapter(Context mContext) {
        super(mContext);
    }

    public SearchAdapter(Context mContext, List<Object> mList) {
        super(mContext, mList);
    }

    @Override
    public RecyclerView.ViewHolder createView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_title , parent , false);
        return new SearchViewHolder(view);
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder, int position) {
        ((SearchViewHolder)holder).bindData(mContext , getList() , position);
    }
}
