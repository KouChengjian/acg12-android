package org.acg12.ui.adapter.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.acg12.R;
import org.acg12.bean.Video;
import org.acg12.utlis.ImageLoadUtils;
import org.acg12.utlis.ViewUtil;
import org.acg12.widget.ScaleImageView;

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
            ImageLoadUtils.universalLoading(url , icon , new SimpleImageLoadingListener(){
                @Override
                public void onLoadingComplete(String imageUri, View view,Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    if(loadedImage.getHeight()> 350){
                        icon.setImageHeight(350);
                    }else{
                        icon.setImageHeight(loadedImage.getHeight());
                    }
                }
            });
        }
        ViewUtil.setText(msg , video.getTitle());
        ViewUtil.setText(num , video.getUpdateContent());
    }
}
