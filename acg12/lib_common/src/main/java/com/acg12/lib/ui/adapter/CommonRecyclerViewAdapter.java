package com.acg12.lib.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acg12.lib.widget.ToolBarView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Android Studio.
 * User: kcj
 * Date: 2018/7/6 11:29
 * Description:
 */
public class CommonRecyclerViewAdapter<T extends CommonRecyclerViewHolder> extends RecyclerView.Adapter{

    protected Context mContext;
    protected List<Object> mList;
    protected int rId;

    public CommonRecyclerViewAdapter(Context mContext, int rId, List mList) {
        this.mContext = mContext;
        this.rId = rId;
        this.mList = mList;
    }

    public void add(Object object) {
        this.mList.add(object);
    }

    public void addAll(List mList) {
        this.mList.addAll(mList);
    }

    public void setList(List mList) {
        this.mList = mList;
    }

    public List getList() {
        return mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

//    @Override
//    public T onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(rId, parent, false);
//        return new T(view);
//    }

//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(rId, parent, false);
//        return new RecyclerView.ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        holder.bindData(mContext , mList , position);
//    }

//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        bindView(holder, position);
//    }
//
//    public abstract RecyclerView.ViewHolder createView(ViewGroup parent, int viewType);
//
//    public abstract void bindView(RecyclerView.ViewHolder holder, int position);
}
