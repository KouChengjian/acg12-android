package com.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acg12.entity.Video;
import com.acg12.ui.adapter.base.BangumiEpisodeViewHolder;

import com.acg12.R;
import com.acg12.entity.Video;
import com.acg12.ui.adapter.base.BangumiEpisodeViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2016/12/28.
 */
public class BangumiEpisodeAdapter extends RecyclerView.Adapter<BangumiEpisodeViewHolder> {

    private Context mContext;
    private List<Video> mList;
    private final LayoutInflater mInflater;

    public BangumiEpisodeAdapter(Context mContext){
        this(mContext , new ArrayList<Video>());
    }

    public BangumiEpisodeAdapter(Context mContext, List<Video> mList){
        this.mContext = mContext;
        this.mList = mList;
        mInflater = LayoutInflater.from(mContext);
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
    public BangumiEpisodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_bangumi_episode, parent, false);
        return new BangumiEpisodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BangumiEpisodeViewHolder holder, int position) {
        holder.bindData(mContext , position ,mList.get(position));
    }
}
