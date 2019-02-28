package com.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.acg12.R;
import com.acg12.entity.Album;
import com.acg12.lib.ui.adapter.CommonRecyclerAdapter;
import com.acg12.ui.adapter.view.CollectAlbumViewHolder;
import com.acg12.ui.adapter.view.SearchAlbumViewHolder;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/2/28 20:21
 * Description:
 */
public class SearchAlbumAdapter extends CommonRecyclerAdapter <Album>{

    private SearchAlbumListener searchAlbumListener;

    public SearchAlbumAdapter(Context mContext) {
        super(mContext);
    }

    public void setSearchAlbumListener(SearchAlbumListener searchAlbumListener) {
        this.searchAlbumListener = searchAlbumListener;
    }

    @Override
    public RecyclerView.ViewHolder createView(ViewGroup parent, int viewType) {
        return new SearchAlbumViewHolder(getItemView(R.layout.item_search_album , parent));
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder, int position) {
        ((SearchAlbumViewHolder) holder).setSearchAlbumListener(searchAlbumListener);
        ((SearchAlbumViewHolder) holder).bindData(mContext, getList(), position);
    }

    public interface SearchAlbumListener{
        void onClickCollect(int position);
    }
}
