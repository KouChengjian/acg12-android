package org.acg12.ui.adapter.base;

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
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;

import org.acg12.R;
import org.acg12.entity.Video;

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
            ImageLoadUtils.glideLoading(url, new Target<Bitmap>(){
                @Override
                public void onStart() {

                }

                @Override
                public void onStop() {

                }

                @Override
                public void onDestroy() {

                }

                @Override
                public void onLoadStarted(Drawable placeholder) {

                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {

                }

                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    if(resource.getHeight()> 350){
                        icon.setImageHeight(350);
                    }else{
                        icon.setImageHeight(resource.getHeight());
                    }
                    icon.setImageBitmap(resource);
                }

                @Override
                public void onLoadCleared(Drawable placeholder) {

                }

                @Override
                public void getSize(SizeReadyCallback cb) {

                }

                @Override
                public void setRequest(Request request) {

                }

                @Override
                public Request getRequest() {
                    return null;
                }
            });
        }
        ViewUtil.setText(msg , video.getTitle());
        ViewUtil.setText(num , video.getUpdateContent());
    }
}
