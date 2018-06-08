package org.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acg12.lib.ui.adapter.CommonRecyclerAdapter;

import org.acg12.R;
import org.acg12.ui.adapter.base.NewestNewsViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/1/26.
 */

public class NewestNewsAdapter extends CommonRecyclerAdapter {

    public NewestNewsAdapter(Context mContext) {
        super(mContext);
    }

    public NewestNewsAdapter(Context mContext, List<Object> mList) {
        super(mContext, mList);
    }

    @Override
    public RecyclerView.ViewHolder createView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_newest_news , parent , false);
        return new NewestNewsViewHolder(view);
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder, int position) {
        ((NewestNewsViewHolder)holder).bindData(mContext , getList() , position);
    }
}
