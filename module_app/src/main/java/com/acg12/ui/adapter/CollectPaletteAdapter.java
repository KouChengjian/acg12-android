package com.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.acg12.R;
import com.acg12.entity.CollectPaletteEntity;
import com.acg12.lib.ui.adapter.CommonRecyclerAdapter;
import com.acg12.ui.adapter.view.CollectPaletteViewHolder;
import com.acg12.ui.adapter.view.SearchPaletteViewHolder;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/3/2 16:37
 * Description:
 */
public class CollectPaletteAdapter extends CommonRecyclerAdapter<CollectPaletteEntity> {

    private SearchPaletteListener searchPaletteListener;

    public CollectPaletteAdapter(Context mContext) {
        super(mContext);
    }

    public void setSearchPaletteListener(SearchPaletteListener searchPaletteListener) {
        this.searchPaletteListener = searchPaletteListener;
    }

    @Override
    public RecyclerView.ViewHolder createView(ViewGroup parent, int viewType) {
        return new CollectPaletteViewHolder(getItemView(R.layout.item_collect_palette, parent));
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder, int position) {
        ((CollectPaletteViewHolder)holder).setSearchPaletteListener(searchPaletteListener);
        ((CollectPaletteViewHolder)holder).bindData(mContext , getList() , position);
    }

    public interface SearchPaletteListener{
        void onClickCollect(int position);
    }
}
