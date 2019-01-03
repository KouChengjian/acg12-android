package com.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.acg12.R;
import com.acg12.entity.CaricatureChaptersEntity;
import com.acg12.lib.ui.adapter.CommonRecyclerAdapter;
import com.acg12.ui.adapter.view.CaricatureChapterViewHolder;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/1/2 19:00
 * Description:
 */
public class CaricatureChapterAdapter extends CommonRecyclerAdapter<CaricatureChaptersEntity> {

    private int index;

    public CaricatureChapterAdapter(Context mContext) {
        super(mContext);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public RecyclerView.ViewHolder createView(ViewGroup parent, int viewType) {
        return new CaricatureChapterViewHolder(getItemView(R.layout.item_caricature_chapter, parent));
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder, int position) {
        ((CaricatureChapterViewHolder) holder).bindData(mContext, getList(), position);
    }

    /**
     * 更新单选
     *
     * @param currPosition 当前位置
     * @param index        位置索引
     */
    public void updatePosition(int currPosition, int index) {
    }

}
