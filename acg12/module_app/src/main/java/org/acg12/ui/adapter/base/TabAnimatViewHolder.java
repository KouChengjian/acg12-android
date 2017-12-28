package org.acg12.ui.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.acg12.kk.utils.ViewUtil;
import com.acg12.kk.utils.loadimage.ImageLoadUtils;
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;

import org.acg12.R;
import org.acg12.entity.Video;

/**
 * Created by DELL on 2016/12/27.
 */
public class TabAnimatViewHolder extends RecyclerView.ViewHolder{
    public static String TAG = "TabAnimatViewHolder";
    FrameLayout video_play_container;
    ImageView video_play_pic;
    ImageView video_click_play;
    TextView video_title;
    TextView video_des;

    ListVideoUtil listVideoUtil;
    RecyclerView.Adapter recyclerBaseAdapter;

    public TabAnimatViewHolder(Context mContext , View itemView) {
        super(itemView);
        video_play_container = (FrameLayout) itemView.findViewById(R.id.video_play_container);

        video_click_play = (ImageView) itemView.findViewById(R.id.video_click_play);
        video_title = (TextView) itemView.findViewById(R.id.video_title);
        video_des = (TextView) itemView.findViewById(R.id.video_des);
//        video_play_pic = new ImageView(mContext);
        video_play_pic = (ImageView) itemView.findViewById(R.id.video_play_pic);
    }

    public void bindData(Context mContext , Video video, final int position){
        String url = video.getPic();
        if(url != null && !url.isEmpty()){
            ImageLoadUtils.glideLoading(mContext , url , video_play_pic);
        }
        ViewUtil.setText(video_title , video.getTitle());
        ViewUtil.setText(video_des , video.getDescription());

        // 视频播放
//        listVideoUtil.addVideoPlayer(position, video_play_pic, TAG,
//                video_play_container, video_click_play);

//        video_click_play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getRecyclerBaseAdapter().notifyDataSetChanged();
//                //listVideoUtil.setLoop(true);
//                listVideoUtil.setPlayPositionAndTag(position, TAG);
//                final String url = "http://baobab.wdjcdn.com/14564977406580.mp4";
//                //listVideoUtil.setCachePath(new File(FileUtils.getPath()));
//                listVideoUtil.startPlay(url);
//            }
//        });
    }

    public RecyclerView.Adapter getRecyclerBaseAdapter() {
        return recyclerBaseAdapter;
    }

    public void setRecyclerBaseAdapter(RecyclerView.Adapter recyclerBaseAdapter) {
        this.recyclerBaseAdapter = recyclerBaseAdapter;
    }

    public ListVideoUtil getListVideoUtil() {
        return listVideoUtil;
    }

    public void setListVideoUtil(ListVideoUtil listVideoUtil) {
        this.listVideoUtil = listVideoUtil;
    }
}
