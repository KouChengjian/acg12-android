package com.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.acg12.R;
import com.acg12.entity.Album;
import com.acg12.lib.ui.adapter.CommonRecyclerAdapter;
import com.acg12.ui.adapter.view.NewestAlbumViewHolder;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/2/22 14:52
 * Description:
 */
public class NewestAlbumAdapter extends CommonRecyclerAdapter<Album> {

    NewestAlbumListener newestAlbumListener;

    public NewestAlbumAdapter(Context mContext) {
        super(mContext);
    }

    public void setNewestAlbumListener(NewestAlbumListener newestAlbumListener) {
        this.newestAlbumListener = newestAlbumListener;
    }

    @Override
    public RecyclerView.ViewHolder createView(ViewGroup parent, int viewType) {
        return new NewestAlbumViewHolder(getItemView(R.layout.item_newest_album, parent));
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder, int position) {
        ((NewestAlbumViewHolder) holder).setNewestAlbumListener(newestAlbumListener);
        ((NewestAlbumViewHolder) holder).bindData(mContext, getList(), position);

    }

    public interface NewestAlbumListener{
        void onClickCollect(int position);
    }
}
