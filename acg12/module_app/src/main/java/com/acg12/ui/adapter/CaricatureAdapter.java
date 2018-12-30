package com.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acg12.R;
import com.acg12.entity.CaricatureEntity;
import com.acg12.lib.ui.adapter.CommonRecyclerAdapter;
import com.acg12.lib.widget.ScaleImageView;
import com.acg12.ui.adapter.view.CaricatureViewHolder;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2018/12/29 14:09
 * Description:
 */
public class CaricatureAdapter extends CommonRecyclerAdapter<CaricatureEntity> {

    public CaricatureAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public RecyclerView.ViewHolder createView(ViewGroup parent, int viewType) {
        return new CaricatureViewHolder(getItemView(R.layout.item_caricature, parent));
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder, int position) {
        ((CaricatureViewHolder) holder).bindData(mContext, getList(), position);
    }
}
