package com.acg12.ui.adapter.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.acg12.R;
import com.acg12.entity.CaricatureChaptersEntity;
import com.acg12.lib.ui.adapter.CommonRecyclerViewHolder;

import java.util.List;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/1/2 19:01
 * Description:
 */
public class CaricatureChapterViewHolder extends CommonRecyclerViewHolder {

    TextView tv_name;

    public CaricatureChapterViewHolder(View itemView) {
        super(itemView);
        tv_name = itemView.findViewById(R.id.tv_name);
    }

    @Override
    public void bindData(Context mContext, List list, int position) {
        super.bindData(mContext, list, position);
        CaricatureChaptersEntity chaptersEntity = (CaricatureChaptersEntity) list.get(position);
        tv_name.setText(chaptersEntity.getTitle());
    }

    /**
     * 更新单选
     *
     * @param currPosition 当前位置
     * @param index        位置索引
     *//*
    public void updatePosition(int currPosition, int index) {
        View lastView = getViewByPosition(lastPosition, R.id.tv_name);
        if (lastView != null)
            lastView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        this.lastPosition = currPosition;
        this.index = index;
        View currView = getViewByPosition(lastPosition, R.id.tv_name);
        if (currView != null)
            currView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorEEE));
    }*/
}
