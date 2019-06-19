package com.acg12.ui.adapter.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.utils.glide.ImageLoadUtils;

import com.acg12.R;
import com.acg12.entity.Video;

/**
 * Created by DELL on 2016/12/28.
 */
public class BangumiSeasonViewHolder extends RecyclerView.ViewHolder {

    public ImageView icon;
    public TextView msg;

    public BangumiSeasonViewHolder(View itemView) {
        super(itemView);
        icon = (ImageView) itemView.findViewById(R.id.iv_album_pic);
        msg = (TextView) itemView.findViewById(R.id.list_item_title);
    }

    public void bindData(Context context , Video video) {
        String url = video.getPic();
        if(url != null && !url.isEmpty()){
//            ImageLoadUtils.universalLoading(url , icon);
            ImageLoadUtils.glideLoading(context ,url , icon);
        }
        ViewUtil.setText(msg , video.getTitle());
    }
}
