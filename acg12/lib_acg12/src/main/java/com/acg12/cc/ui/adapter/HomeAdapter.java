package com.acg12.cc.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 */
public class HomeAdapter extends CCBaseRecyAdapter {

    public HomeAdapter(Context mContext) {
        super(mContext);
    }

    public HomeAdapter(Context mContext, List<Object> mList) {
        super(mContext, mList);
    }

    @Override
    public RecyclerView.ViewHolder createView(ViewGroup parent, int viewType) {
        return super.createView(parent, viewType);
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder, int position) {
        super.bindView(holder, position);
    }
}
