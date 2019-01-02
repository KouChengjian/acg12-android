package com.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acg12.ui.adapter.view.TabPaletteViewHolder;

import com.acg12.R;
import com.acg12.entity.Palette;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2016/12/23.
 */
public class TabPaletteAdapter extends RecyclerView.Adapter<TabPaletteViewHolder>{

    private Context mContext;
    private List<Palette> mList;

    public TabPaletteAdapter(Context mContext){
        this(mContext , new ArrayList<Palette>());
    }

    public TabPaletteAdapter(Context mContext, List<Palette> mList){
        this.mContext = mContext;
        this.mList = mList;
    }

    public void addAll(List<Palette> mList){
        this.mList.addAll(mList);
    }

    public void add(Palette home){
        this.mList.add(home);
    }

    public void setList(List<Palette> mList){
        this.mList =  mList;
    }

    public List<Palette> getList(){
        return mList;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public TabPaletteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tab_palette, parent, false);
        return new TabPaletteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TabPaletteViewHolder holder, int position) {
        holder.bindData(mContext,mList.get(position));
    }
}
