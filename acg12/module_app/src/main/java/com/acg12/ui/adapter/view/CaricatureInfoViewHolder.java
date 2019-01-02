package com.acg12.ui.adapter.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acg12.R;
import com.acg12.entity.CaricatureChaptersEntity;
import com.acg12.lib.ui.adapter.CommonRecyclerViewHolder;

import java.util.List;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/1/2 16:23
 * Description:
 */
public class CaricatureInfoViewHolder extends CommonRecyclerViewHolder {

//    public CaricatureInfoViewHolder(Context context, ViewGroup viewGroup) {
//        this(getItemView(context, R.layout.item_caricature_vertical, viewGroup));
//        this(LayoutInflater.from(context).inflate(R.layout.item_caricature_vertical, viewGroup, false));
//    }

    public CaricatureInfoViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(Context mContext, List list, int position) {
        super.bindData(mContext, list, position);
        CaricatureChaptersEntity chaptersEntity = (CaricatureChaptersEntity)list.get(position);
    }
}
