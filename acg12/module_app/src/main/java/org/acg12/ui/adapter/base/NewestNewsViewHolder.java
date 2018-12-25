package org.acg12.ui.adapter.base;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acg12.lib.ui.adapter.CommonRecyclerViewHolder;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.utils.glide.ImageLoadUtils;

import org.acg12.R;
import org.acg12.entity.News;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/1/26.
 */

public class NewestNewsViewHolder extends CommonRecyclerViewHolder {

    private RelativeLayout layout_header;
    private TextView tv_header_title;
    private ImageView iv_news_cover;
    private TextView tv_news_title;
    private HashMap<String, String> headers = new HashMap<>();

    public NewestNewsViewHolder(View itemView) {
        super(itemView);
        layout_header = (RelativeLayout) itemView.findViewById(R.id.layout_header);
        tv_header_title = (TextView) itemView.findViewById(R.id.tv_header_title);
        iv_news_cover = (ImageView) itemView.findViewById(R.id.iv_news_cover);
        tv_news_title = (TextView) itemView.findViewById(R.id.tv_news_title);
    }

    @Override
    public void bindData(Context mContext, List list, int position) {
        News news = (News) list.get(position);
        LogUtil.e(news.getCover());
        ViewUtil.setText(tv_news_title, news.getTitle());
        headers.put("Referer", "http://images.dmzj.com/");
        ImageLoadUtils.glideLoading(mContext, headers, news.getCover(), iv_news_cover);

        if (position == 0) {
            layout_header.setVisibility(View.VISIBLE);
            SimpleDateFormat sdf = new SimpleDateFormat("MM年dd月");
            String curDate = sdf.format(new Date(news.getCreateTime() * 1000l));
            tv_header_title.setText(curDate);
        } else {
            int curTime = news.getCreateTime();
            News lastNews = (News) list.get(position - 1);
            int lastTime = lastNews.getCreateTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String curDate = sdf.format(new Date(curTime * 1000l));
            String lastDate = sdf.format(new Date(lastTime * 1000l));

            if (curDate.equals(lastDate)) {
                layout_header.setVisibility(View.GONE);
            } else {
                layout_header.setVisibility(View.VISIBLE);
                tv_header_title.setText(new SimpleDateFormat("MM年dd月").format(new Date(news.getCreateTime() * 1000l)));
            }
        }
    }
}
