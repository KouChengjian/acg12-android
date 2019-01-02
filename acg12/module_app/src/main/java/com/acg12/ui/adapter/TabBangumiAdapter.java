package com.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acg12.entity.Video;
import com.acg12.ui.adapter.view.TabBangumiViewHolder;

import com.acg12.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2016/12/24.
 */
public class TabBangumiAdapter extends RecyclerView.Adapter<TabBangumiViewHolder>{

    private Context mContext;
    private List<Video> mList;

    public TabBangumiAdapter(Context mContext){
        this(mContext , new ArrayList<Video>());
    }

    public TabBangumiAdapter(Context mContext, List<Video> mList){
        this.mContext = mContext;
        this.mList = mList;
    }

    public void addAll(List<Video> mList){
        this.mList.addAll(mList);
    }

    public void add(Video home){
        this.mList.add(home);
    }

    public void setList(List<Video> mList){
        this.mList =  mList;
    }

    public List<Video> getList(){
        return mList;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public TabBangumiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tab_bangumi, parent, false);
        return new TabBangumiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TabBangumiViewHolder holder, int position) {
        holder.bindData(mContext , mList.get(position));
    }
}
