package org.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.acg12.entity.DownLoad;

import org.acg12.R;
import org.acg12.ui.adapter.base.DownloadViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */
public class DownloadAdapter extends RecyclerView.Adapter<DownloadViewHolder>{
    private Context mContext;
    private List<DownLoad> mList;

    public DownloadAdapter(Context mContext){
        this(mContext , new ArrayList<DownLoad>());
    }

    public DownloadAdapter(Context mContext, List<DownLoad> mList){
        this.mContext = mContext;
        this.mList = mList;
    }

    public void addAll(List<DownLoad> mList){
        this.mList.addAll(mList);
    }

    public void add(DownLoad home){
        this.mList.add(home);
    }

    public void setList(List<DownLoad> mList){
        this.mList =  mList;
    }

    public List<DownLoad> getList(){
        return mList;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public DownloadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_download, parent, false);
        return new DownloadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DownloadViewHolder holder, int position) {
        holder.bindData(mContext,mList.get(position));
    }
}
