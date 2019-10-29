package com.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.acg12.lib.ui.adapter.CommonRecyclerAdapter;
import com.acg12.lib.ui.adapter.CommonRecyclerViewHolder;

/**
 * Created with Android Studio.
 * User: KCJ
 * Date: 2019-07-08 11:10
 * Description:
 */
public class HomeAdapter extends CommonRecyclerAdapter {

    public HomeAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public RecyclerView.ViewHolder createView(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder, int position) {

    }

    public class ViewHolder extends CommonRecyclerViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
