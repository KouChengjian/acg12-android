package com.acg12.lib.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2018/7/6 11:29
 * Description:
 */
public abstract  class CommonRecyclerViewHolder extends RecyclerView.ViewHolder {

    public CommonRecyclerViewHolder(View itemView) {
        super(itemView);
        initViews(itemView);
        initEvent();
    }

    protected void initViews(View itemView) {}

    protected void initEvent() {}


    public void bindData(Context mContext, final List list, int position){

    }
}
