package com.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acg12.ui.adapter.view.TabAlbumViewHolder;

import com.acg12.R;
import com.acg12.entity.Album;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2016/12/23.
 */
public class TabAlbumAdapter extends RecyclerView.Adapter<TabAlbumViewHolder>{

    private Context mContext;
    private List<Album> mList;

    public TabAlbumAdapter(Context mContext){
        this(mContext , new ArrayList<Album>());
    }

    public TabAlbumAdapter(Context mContext, List<Album> mList){
        this.mContext = mContext;
        this.mList = mList;
    }

    public void addAll(List<Album> mList){
        this.mList.addAll(mList);
    }

    public void add(Album home){
        this.mList.add(home);
    }

    public void setList(List<Album> mList){
        this.mList =  mList;
    }

    public List<Album> getList(){
        return mList;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public TabAlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tab_album, parent, false);
        return new TabAlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TabAlbumViewHolder holder, int position) {
        holder.bindData(mContext,mList,position);
    }
}
