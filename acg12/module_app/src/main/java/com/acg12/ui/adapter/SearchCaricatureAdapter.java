package com.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.acg12.R;
import com.acg12.entity.CaricatureEntity;
import com.acg12.lib.ui.adapter.CommonRecyclerAdapter;
import com.acg12.ui.adapter.view.SearchCaricatureViewHolder;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2018/12/29 14:09
 * Description:
 */
public class SearchCaricatureAdapter extends CommonRecyclerAdapter<CaricatureEntity> {

    private SearchCaricatureListener searchCaricatureListener;

    public SearchCaricatureAdapter(Context mContext) {
        super(mContext);
    }

    public void setSearchCaricatureListener(SearchCaricatureListener searchCaricatureListener) {
        this.searchCaricatureListener = searchCaricatureListener;
    }

    @Override
    public RecyclerView.ViewHolder createView(ViewGroup parent, int viewType) {
        return new SearchCaricatureViewHolder(getItemView(R.layout.item_search_caricature, parent));
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder, int position) {
        ((SearchCaricatureViewHolder) holder).setSearchCaricatureListener(searchCaricatureListener);
        ((SearchCaricatureViewHolder) holder).bindData(mContext, getList(), position);
    }

    public interface SearchCaricatureListener {
        void onClickCollect(int position);
    }
}
