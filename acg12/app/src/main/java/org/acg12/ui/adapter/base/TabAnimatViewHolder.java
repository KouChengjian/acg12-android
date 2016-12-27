package org.acg12.ui.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.acg12.R;
import org.acg12.bean.Video;
import org.acg12.utlis.ImageLoadUtils;
import org.acg12.utlis.ViewUtil;

/**
 * Created by DELL on 2016/12/27.
 */
public class TabAnimatViewHolder extends RecyclerView.ViewHolder{

    ImageView video_play_pic;
    TextView video_title;
    TextView video_des;

    public TabAnimatViewHolder(View itemView) {
        super(itemView);
        video_play_pic = (ImageView) itemView.findViewById(R.id.video_play_pic);
        video_title = (TextView) itemView.findViewById(R.id.video_title);
        video_des = (TextView) itemView.findViewById(R.id.video_des);
    }

    public void bindData(Context mContext , Video video){
        String url = video.getPic();
        if(url != null && !url.isEmpty()){
            ImageLoadUtils.glideLoading(mContext,url , video_play_pic);
        }
        ViewUtil.setText(video_title , video.getTitle());
        ViewUtil.setText(video_des , video.getDescription());
    }
}
