package com.acg12.kk.ui.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2017/12/13.
 */
public class KKBaseRecyclerView extends RecyclerView.ViewHolder {

    public KKBaseRecyclerView(View itemView) {
        super(itemView);
        initViews(itemView);
        initEvent();
    }

    protected void initViews(View itemView) {}

    protected void initEvent() {}


    public void bindData(Context mContext, final List<Object> list, int position) {}
}
