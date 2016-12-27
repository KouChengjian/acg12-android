package org.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.acg12.R;
import org.acg12.bean.Home;
import org.acg12.ui.adapter.base.HomeViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2016/12/7.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder>{

    private Context mContext;
    private List<Home> mList;
    private final LayoutInflater mInflater;

    public HomeAdapter(Context mContext){
        this(mContext , new ArrayList<Home>());
    }

    public HomeAdapter(Context mContext, List<Home> mList){
        this.mContext = mContext;
        this.mList = mList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void addAll(List<Home> mList){
        this.mList.addAll(mList);
    }

    public void add(Home home){
        this.mList.add(home);
    }

    public void setList(List<Home> mList){
        this.mList =  mList;
    }

    public List<Home> getList(){
        return mList;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_home, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        holder.bindData(position);
    }


}
