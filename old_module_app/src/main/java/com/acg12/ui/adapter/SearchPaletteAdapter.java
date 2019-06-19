package com.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.acg12.R;
import com.acg12.entity.Palette;
import com.acg12.lib.ui.adapter.CommonRecyclerAdapter;
import com.acg12.ui.adapter.view.SearchPaletteViewHolder;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/2/28 20:35
 * Description:
 */
public class SearchPaletteAdapter extends CommonRecyclerAdapter<Palette> {

    private SearchPaletteListener searchPaletteListener;

    public SearchPaletteAdapter(Context mContext) {
        super(mContext);
    }

    public void setSearchPaletteListener(SearchPaletteListener searchPaletteListener) {
        this.searchPaletteListener = searchPaletteListener;
    }

    @Override
    public RecyclerView.ViewHolder createView(ViewGroup parent, int viewType) {
        return new SearchPaletteViewHolder(getItemView(R.layout.item_search_palette, parent));
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder, int position) {
        ((SearchPaletteViewHolder)holder).setSearchPaletteListener(searchPaletteListener);
        ((SearchPaletteViewHolder)holder).bindData(mContext , getList() , position);
    }

    public interface SearchPaletteListener{
        void onClickCollect(int position);
    }
}
