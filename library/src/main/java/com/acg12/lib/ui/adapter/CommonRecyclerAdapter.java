package com.acg12.lib.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 */
public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter {

    protected Context mContext;
    protected List<T> mList;
    protected LayoutInflater mLayoutInflater;

    public CommonRecyclerAdapter(Context mContext) {
        this(mContext, new ArrayList<T>());
    }

    public CommonRecyclerAdapter(Context mContext, List mList) {
        this.mContext = mContext;
        this.mList = mList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void add(T object) {
        this.mList.add(object);
    }

    public void del(T object) {
        this.mList.remove(object);
    }

    public void del(int index) {
        this.mList.remove(index);
    }

    public void addAll(List mList) {
        this.mList.addAll(mList);
    }

    public void setList(List mList) {
        this.mList = mList;
    }

    public List<T> getList() {
        return mList;
    }

    public T getObject(int position){
        return getList().get(position);
    }

    public View getItemView(int rid, ViewGroup parent) {
        return mLayoutInflater.inflate(rid, parent, false);
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
