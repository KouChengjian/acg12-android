package com.acg12.ui.adapter.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acg12.lib.ui.adapter.CommonRecyclerViewHolder;
import com.acg12.lib.utils.PixelUtil;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.utils.glide.ImageLoadUtils;

import com.acg12.R;
import com.acg12.entity.Tags;

import java.util.List;

/**
 * Created by Administrator on 2018/1/16.
 */

public class HomeTagViewHolder extends CommonRecyclerViewHolder {

    RelativeLayout layout_tag;
    ImageView iv_tag_cover;
    TextView tv_tag_title;

    public HomeTagViewHolder(View itemView) {
        super(itemView);
        layout_tag =  itemView.findViewById(R.id.layout_tag);
        iv_tag_cover =  itemView.findViewById(R.id.iv_tag_cover);
        tv_tag_title =  itemView.findViewById(R.id.tv_tag_title);
    }

    @Override
    public void bindData(Context mContext, List list, int position) {
        Tags tags = (Tags) list.get(position);
        ViewGroup.LayoutParams layoutParams = layout_tag.getLayoutParams();
        layoutParams.height = (PixelUtil.getScreenWidthPx(mContext) - (PixelUtil.dp2px(mContext , 20) * 3)) / 2 ;
        layout_tag.setLayoutParams(layoutParams);

        ImageLoadUtils.glideLoading(mContext,tags.getCover() , iv_tag_cover);
        ViewUtil.setText(tv_tag_title , tags.getName());
    }
}
