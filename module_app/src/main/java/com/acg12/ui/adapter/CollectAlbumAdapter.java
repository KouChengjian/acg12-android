package com.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.acg12.R;
import com.acg12.entity.Album;
import com.acg12.lib.ui.adapter.CommonRecyclerAdapter;
import com.acg12.ui.adapter.view.CollectAlbumViewHolder;
import com.acg12.ui.adapter.view.NewestAlbumViewHolder;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/2/22 20:32
 * Description:
 */
public class CollectAlbumAdapter extends CommonRecyclerAdapter<Album> {

    CollectAlbumListener collectAlbumListener;

    public CollectAlbumAdapter(Context mContext) {
        super(mContext);
    }

    public void setCollectAlbumListener(CollectAlbumListener collectAlbumListener) {
        this.collectAlbumListener = collectAlbumListener;
    }

    @Override
    public RecyclerView.ViewHolder createView(ViewGroup parent, int viewType) {
        return new CollectAlbumViewHolder(getItemView(R.layout.item_collect_album ,parent));
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder, int position) {
        ((CollectAlbumViewHolder) holder).setCollectAlbumListener(collectAlbumListener);
        ((CollectAlbumViewHolder) holder).bindData(mContext, getList(), position);
    }

    public interface CollectAlbumListener{
        void onClickCollect(int position);
    }
}
