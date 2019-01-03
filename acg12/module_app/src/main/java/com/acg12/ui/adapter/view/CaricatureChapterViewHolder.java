package com.acg12.ui.adapter.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.acg12.R;
import com.acg12.entity.CaricatureChaptersEntity;
import com.acg12.lib.ui.adapter.CommonRecyclerViewHolder;
import com.acg12.lib.utils.LogUtil;
import com.acg12.ui.adapter.CaricatureChapterAdapter;

import java.util.List;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/1/2 19:01
 * Description:
 */
public class CaricatureChapterViewHolder extends CommonRecyclerViewHolder {

    TextView tv_name;
    private CaricatureChapterAdapter.OnCaricatureChapterListener onCaricatureChapterListener;

    public CaricatureChapterViewHolder(View itemView) {
        super(itemView);
        tv_name = itemView.findViewById(R.id.tv_name);
    }

    public void setOnCaricatureChapterListener(CaricatureChapterAdapter.OnCaricatureChapterListener onCaricatureChapterListener) {
        this.onCaricatureChapterListener = onCaricatureChapterListener;
    }

    public void bindData(Context mContext, List list, final int position, int index, int lastPosition) {
        super.bindData(mContext, list, position);
        final CaricatureChaptersEntity chaptersEntity = (CaricatureChaptersEntity) list.get(position);
        tv_name.setText(chaptersEntity.getTitle());
        if (index == chaptersEntity.getIndex()) {
            lastPosition = position;
            tv_name.setBackgroundColor(ContextCompat.getColor(mContext, R.color.background));
        } else if (lastPosition != -1 && lastPosition == position) {
            tv_name.setBackgroundColor(ContextCompat.getColor(mContext, R.color.background));
        } else {
            tv_name.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        }

        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onCaricatureChapterListener != null){
                    onCaricatureChapterListener.onClickChapter(chaptersEntity ,position);
                }
            }
        });
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
