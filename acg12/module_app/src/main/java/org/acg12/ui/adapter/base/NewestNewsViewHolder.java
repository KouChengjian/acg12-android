package org.acg12.ui.adapter.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acg12.lib.ui.adapter.CommonRecyclerView;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.utils.glide.ImageLoadUtils;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.acg12.R;
import org.acg12.entity.News;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/26.
 */

public class NewestNewsViewHolder extends CommonRecyclerView {

    private RelativeLayout layout_header;
    TextView tv_header_title;

    private ImageView iv_news_cover;
    TextView tv_news_title;

    public NewestNewsViewHolder(View itemView) {
        super(itemView);
        layout_header = (RelativeLayout) itemView.findViewById(R.id.layout_header);
        tv_header_title = (TextView) itemView.findViewById(R.id.tv_header_title);
        iv_news_cover = (ImageView) itemView.findViewById(R.id.iv_news_cover);
        tv_news_title = (TextView) itemView.findViewById(R.id.tv_news_title);
    }

    @Override
    public void bindData(Context mContext, List list, int position) {
        News news  = (News)list.get(position);
        LogUtil.e(news.getCover());
//        ImageLoadUtils.glideLoading(news.getCover() , iv_news_cover);
        ViewUtil.setText(tv_news_title , news.getTitle());

        ImageLoadUtils.glideLoading(mContext , news.getCover() , new SimpleTarget<Bitmap>(){
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                LogUtil.e(resource.getHeight()+"=========");
                iv_news_cover.setImageBitmap(resource);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                LogUtil.e(e.toString());
            }
        });

        if(position == 0){
            layout_header.setVisibility(View.VISIBLE);
            SimpleDateFormat sdf = new SimpleDateFormat("MM年dd月");
            String curDate = sdf.format(new Date(news.getCreateTime() * 1000l));
            tv_header_title.setText(curDate);
        } else {
            int curTime = news.getCreateTime();
            News lastNews = (News)list.get(position - 1);
            int lastTime = lastNews.getCreateTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String curDate = sdf.format(new Date(curTime* 1000l));
            String lastDate = sdf.format(new Date(lastTime* 1000l));

            if(curDate.equals(lastDate)){
                layout_header.setVisibility(View.GONE);
            } else {
                layout_header.setVisibility(View.VISIBLE);
                tv_header_title.setText( new SimpleDateFormat("MM年dd月").format(new Date(news.getCreateTime() * 1000l)));
            }
        }
    }
}
