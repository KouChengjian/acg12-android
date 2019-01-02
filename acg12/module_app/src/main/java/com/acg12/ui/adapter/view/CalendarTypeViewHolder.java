package com.acg12.ui.adapter.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.acg12.R;
import com.acg12.entity.Calendar;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.utils.glide.ImageLoadUtils;
import com.acg12.lib.widget.ScaleImageView;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2018/7/3 15:27
 * Description:
 */
public class CalendarTypeViewHolder extends RecyclerView.ViewHolder {

    public ScaleImageView icon;
    public TextView msg;
    public TextView num;

    public CalendarTypeViewHolder(View itemView) {
        super(itemView);
        icon = (ScaleImageView) itemView.findViewById(R.id.iv_album_pic);
        msg = (TextView) itemView.findViewById(R.id.list_item_title);
        num = (TextView) itemView.findViewById(R.id.list_item_num);
    }

    public void bindData(Context context, Calendar calendar) {
        icon.setImageWidth(250);
        icon.setImageHeight(350);
        ViewUtil.setText(msg, calendar.getName());
        ViewUtil.setText(num, calendar.getAir_date());
        String url = calendar.getImage();
        ImageLoadUtils.glideLoading(context, url, icon);
//        if(url != null && !url.isEmpty()){
//            ImageLoadUtils.glideLoading(context ,url, new SimpleTarget<Bitmap>(){
//                @Override
//                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                    if(resource.getHeight()> 350){
//                        icon.setImageHeight(350);
//                    }else{
//                        icon.setImageHeight(resource.getHeight());
//                    }
//                    icon.setImageBitmap(resource);
//                }
//            });
//        }
    }
}
