package com.acg12.ui.adapter.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.acg12.R;
import com.acg12.entity.CaricatureEntity;
import com.acg12.lib.ui.adapter.CommonRecyclerViewHolder;
import com.acg12.lib.utils.glide.ImageLoadUtils;
import com.acg12.lib.widget.ScaleImageView;

import java.util.List;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2018/12/29 14:13
 * Description:
 */
public class CaricatureViewHolder extends CommonRecyclerViewHolder {

    ScaleImageView ivCaricatureCover;
    TextView tvCaricatureNum;
    TextView tvCaricatureTitle;

    public CaricatureViewHolder(View itemView) {
        super(itemView);
        ivCaricatureCover = (ScaleImageView) itemView.findViewById(R.id.iv_caricature_cover);
        tvCaricatureNum = (TextView)itemView.findViewById(R.id.tv_caricature_num);
        tvCaricatureTitle = (TextView)itemView.findViewById(R.id.tv_caricature_title);
    }

    public void bindData(Context mContext, List list, int position) {
        CaricatureEntity caricatureEntity = (CaricatureEntity)list.get(position);
        ivCaricatureCover.setImageWidth(250);
        ivCaricatureCover.setImageHeight(350);
        ImageLoadUtils.glideLoading(mContext , caricatureEntity.getCover() , ivCaricatureCover);
        tvCaricatureTitle.setText(caricatureEntity.getTitle());
    }
}
