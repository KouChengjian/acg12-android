package com.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.acg12.R;
import com.acg12.entity.Album;
import com.acg12.lib.ui.adapter.CommonRecyclerAdapter;
import com.acg12.ui.adapter.view.PaletteInfoViewHolder;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/3/25 16:29
 * Description:
 */
public class PaletteInfoAdapter extends CommonRecyclerAdapter<Album> {

    private PaletteInfoListener paletteInfoListener;

    public PaletteInfoAdapter(Context mContext) {
        super(mContext);
    }

    public void setPaletteInfoListener(PaletteInfoListener paletteInfoListener) {
        this.paletteInfoListener = paletteInfoListener;
    }

    @Override
    public RecyclerView.ViewHolder createView(ViewGroup parent, int viewType) {
        return new PaletteInfoViewHolder(getItemView(R.layout.item_palette_info, parent));
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder, int position) {
        ((PaletteInfoViewHolder) holder).setPaletteInfoListener(paletteInfoListener);
        ((PaletteInfoViewHolder) holder).bindData(mContext, getList(), position);
    }

    public interface PaletteInfoListener {
        void onClickCollect(int position);
    }
}
