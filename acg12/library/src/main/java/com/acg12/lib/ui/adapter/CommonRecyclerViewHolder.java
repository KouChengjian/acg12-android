package com.acg12.lib.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2017/12/13.
 */
public abstract class CommonRecyclerViewHolder extends RecyclerView.ViewHolder {

    public CommonRecyclerViewHolder(View itemView) {
        super(itemView);
        initViews(itemView);
        initEvent();
    }

    protected void initViews(View itemView) {}

    protected void initEvent() {}


    public abstract void bindData(Context mContext, final List list, int position) ;
}
