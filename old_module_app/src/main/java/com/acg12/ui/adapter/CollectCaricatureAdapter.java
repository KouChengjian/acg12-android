package com.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.acg12.R;
import com.acg12.entity.CollectCaricatureEntity;
import com.acg12.lib.ui.adapter.CommonRecyclerAdapter;
import com.acg12.ui.adapter.view.CollectCaricatureViewHolder;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/3/20 15:45
 * Description:
 */
public class CollectCaricatureAdapter extends CommonRecyclerAdapter<CollectCaricatureEntity> {

    private CollectCaricatureListener collectCaricatureListener;

    public CollectCaricatureAdapter(Context mContext) {
        super(mContext);
    }

    public void setCollectCaricatureListener(CollectCaricatureListener collectCaricatureListener) {
        this.collectCaricatureListener = collectCaricatureListener;
    }

    @Override
    public RecyclerView.ViewHolder createView(ViewGroup parent, int viewType) {
        return new CollectCaricatureViewHolder(getItemView(R.layout.item_collect_caricature, parent));
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder, int position) {
        ((CollectCaricatureViewHolder) holder).setCollectCaricatureListener(collectCaricatureListener);
        ((CollectCaricatureViewHolder) holder).bindData(mContext, getList(), position);
    }

    public interface CollectCaricatureListener {
        void onClickCollect(int position);
    }
}
