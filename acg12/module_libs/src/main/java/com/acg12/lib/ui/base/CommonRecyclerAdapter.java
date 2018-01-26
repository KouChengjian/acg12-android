package com.acg12.lib.ui.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 */
public abstract class CommonRecyclerAdapter extends RecyclerView.Adapter {

    protected Context mContext;
    protected List<Object> mList;

    public CommonRecyclerAdapter(Context mContext) {
        this(mContext, new ArrayList<Object>());
    }

    public CommonRecyclerAdapter(Context mContext, List<Object> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public void add(Object object) {
        this.mList.add(object);
    }

    public void addAll(List<Object> mList) {
        this.mList.addAll(mList);
    }

    public void setList(List<Object> mList) {
        this.mList = mList;
    }

    public List<Object> getList() {
        return mList;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return createView(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindView(holder, position);
    }

    public abstract RecyclerView.ViewHolder createView(ViewGroup parent, int viewType);

    public abstract void bindView(RecyclerView.ViewHolder holder, int position) ;

//    public RecyclerView.ViewHolder createView(ViewGroup parent, int viewType) {
//        return null;
//    }
//
//    public void bindView(RecyclerView.ViewHolder holder, int position) {
//
//    }
}
