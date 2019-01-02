package com.acg12.ui.adapter.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.utils.glide.ImageLoadUtils;
import com.acg12.lib.widget.ScaleImageView;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;

import com.acg12.R;
import com.acg12.entity.Video;
import com.bumptech.glide.request.transition.Transition;

/**
 * Created by DELL on 2016/12/24.
 */
public class TabBangumiViewHolder extends RecyclerView.ViewHolder {

    public ScaleImageView icon;
    public TextView msg;
    public TextView num;

    public TabBangumiViewHolder(View itemView) {
        super(itemView);
        icon = (ScaleImageView) itemView.findViewById(R.id.iv_album_pic);
        msg = (TextView) itemView.findViewById(R.id.list_item_title);
        num = (TextView) itemView.findViewById(R.id.list_item_num);
    }

    public void bindData(Context context ,Video video) {
        icon.setImageWidth(250);
        icon.setImageHeight(350);
        String url = video.getPic();
        if(url != null && !url.isEmpty()){
            ImageLoadUtils.glideLoading(context,url, new SimpleTarget<Bitmap>(){
                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                    if(resource.getHeight()> 350){
                        icon.setImageHeight(350);
                    }else{
                        icon.setImageHeight(resource.getHeight());
                    }
                    icon.setImageBitmap(resource);
                }
            });
        }
        ViewUtil.setText(msg , video.getTitle());
        ViewUtil.setText(num , video.getUpdateContent());
    }
}
