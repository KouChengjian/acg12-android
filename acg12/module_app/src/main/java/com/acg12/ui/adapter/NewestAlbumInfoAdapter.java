package com.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.acg12.R;
import com.acg12.entity.Album;
import com.acg12.entity.CaricatureChaptersEntity;
import com.acg12.lib.ui.adapter.CommonRecyclerAdapter;
import com.acg12.ui.adapter.view.NewestAlbumInfoViewHolder;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/3/23 10:23
 * Description:
 */
public class NewestAlbumInfoAdapter extends CommonRecyclerAdapter<Album> {

    public NewestAlbumInfoAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public RecyclerView.ViewHolder createView(ViewGroup parent, int viewType) {
        return new NewestAlbumInfoViewHolder(getItemView(R.layout.item_newest_album_info, parent));
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder, int position) {
        ((NewestAlbumInfoViewHolder) holder).bindData(mContext, getList(), position);
    }
}
