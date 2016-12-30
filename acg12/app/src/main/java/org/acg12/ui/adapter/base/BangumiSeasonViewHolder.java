package org.acg12.ui.adapter.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.acg12.R;
import org.acg12.bean.Video;
import org.acg12.utlis.ImageLoadUtils;
import org.acg12.utlis.ViewUtil;
import org.acg12.widget.ScaleImageView;

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
            ImageLoadUtils.universalLoading(url , icon);
        }
        ViewUtil.setText(msg , video.getTitle());
    }
}
