package org.acg12.ui.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.acg12.lib.utils.ViewUtil;

import org.acg12.R;
import org.acg12.entity.Video;

/**
 * Created by DELL on 2016/12/28.
 */
public class BangumiEpisodeViewHolder extends RecyclerView.ViewHolder {

    TextView episode_num;

    public BangumiEpisodeViewHolder(View itemView) {
        super(itemView);
        episode_num = (TextView) itemView.findViewById(R.id.episode_num);
    }

    public void bindData(Context mContext ,int position , Video video){
        ViewUtil.setText(episode_num , position + 1);
    }
}
