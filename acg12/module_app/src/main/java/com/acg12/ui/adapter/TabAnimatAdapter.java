package com.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acg12.ui.adapter.view.TabAnimatViewHolder;
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;

import com.acg12.R;
import com.acg12.entity.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2016/12/27.
 */
public class TabAnimatAdapter extends RecyclerView.Adapter<TabAnimatViewHolder> {

    private Context mContext;
    private List<Video> mList;
    private final LayoutInflater mInflater;
    private ListVideoUtil listVideoUtil;

    public TabAnimatAdapter(Context mContext){
        this(mContext , new ArrayList<Video>());
    }

    public TabAnimatAdapter(Context mContext, List<Video> mList){
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
    public TabAnimatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_tab_animat, parent, false);
        return new TabAnimatViewHolder(mContext,view);
    }

    @Override
    public void onBindViewHolder(TabAnimatViewHolder holder, int position) {
        holder.setListVideoUtil(listVideoUtil);
        holder.setRecyclerBaseAdapter(this);
        holder.bindData(mContext ,mList.get(position) ,position);
    }

    public ListVideoUtil getListVideoUtil() {
        return listVideoUtil;
    }

    public void setListVideoUtil(ListVideoUtil listVideoUtil) {
        this.listVideoUtil = listVideoUtil;
    }

    public void startPlay(String url , String danmu){

    }
}
