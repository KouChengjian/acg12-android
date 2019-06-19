package com.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.acg12.R;
import com.acg12.entity.CollectSubjectEntity;
import com.acg12.lib.ui.adapter.CommonRecyclerAdapter;
import com.acg12.ui.adapter.view.CollectSubjectViewHolder;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/2/28 17:34
 * Description:
 */
public class CollectSubjectAdapter extends CommonRecyclerAdapter<CollectSubjectEntity> {

    private CollectSubjectListener collectSubjectListener;

    public CollectSubjectAdapter(Context mContext) {
        super(mContext);
    }

    public void setCollectSubjectListener(CollectSubjectListener collectSubjectListener) {
        this.collectSubjectListener = collectSubjectListener;
    }

    @Override
    public RecyclerView.ViewHolder createView(ViewGroup parent, int viewType) {
        return new CollectSubjectViewHolder(getItemView(R.layout.item_collect_subject, parent));
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder, int position) {
        ((CollectSubjectViewHolder) holder).setCollectSubjectListener(collectSubjectListener);
        ((CollectSubjectViewHolder) holder).bindData(mContext, getList(), position);
    }

    public interface CollectSubjectListener {
        void onClickCollect(int position);
    }
}
