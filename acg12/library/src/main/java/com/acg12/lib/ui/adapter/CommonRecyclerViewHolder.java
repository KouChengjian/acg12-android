package com.acg12.lib.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/12/13.
 */
public abstract class CommonRecyclerViewHolder extends RecyclerView.ViewHolder {

    protected LayoutInflater mLayoutInflater;

    public CommonRecyclerViewHolder(View itemView) {
        super(itemView);
        initViews(itemView);
        initEvent();
    }

    protected void initViews(View itemView) {}

    protected void initEvent() {}

//    public CommonRecyclerViewHolder(Context context, int rId, ViewGroup parent) {
//        mLayoutInflater = LayoutInflater.from(context);
//        this(getItemView(rId, parent));
//    }
//
//    public View getItemView(int rid, ViewGroup parent) {
//        return mLayoutInflater.inflate(rid, parent, false);
//    }

    public void bindData(Context mContext, final List list, int position) {
    }
}
