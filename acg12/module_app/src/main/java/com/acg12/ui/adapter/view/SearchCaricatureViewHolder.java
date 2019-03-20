package com.acg12.ui.adapter.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.acg12.R;
import com.acg12.entity.CaricatureEntity;
import com.acg12.lib.ui.adapter.CommonRecyclerViewHolder;
import com.acg12.lib.utils.glide.ImageLoadUtils;
import com.acg12.lib.widget.BGButton;
import com.acg12.lib.widget.ScaleImageView;
import com.acg12.ui.adapter.SearchCaricatureAdapter;

import java.util.List;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2018/12/29 14:13
 * Description:
 */
public class SearchCaricatureViewHolder extends CommonRecyclerViewHolder {

    ScaleImageView ivCaricatureCover;
    TextView tvCaricatureNum;
    TextView tvCaricatureTitle;
    BGButton btnCaricatureCollect;

    private SearchCaricatureAdapter.SearchCaricatureListener searchCaricatureListener;

    public SearchCaricatureViewHolder(View itemView) {
        super(itemView);
        ivCaricatureCover = itemView.findViewById(R.id.iv_caricature_cover);
        tvCaricatureNum = itemView.findViewById(R.id.tv_caricature_num);
        tvCaricatureTitle = itemView.findViewById(R.id.tv_caricature_title);
        btnCaricatureCollect = itemView.findViewById(R.id.btn_caricature_collect);
    }

    public void setSearchCaricatureListener(SearchCaricatureAdapter.SearchCaricatureListener searchCaricatureListener) {
        this.searchCaricatureListener = searchCaricatureListener;
    }

    public void bindData(Context mContext, List list, final int position) {
        CaricatureEntity caricatureEntity = (CaricatureEntity) list.get(position);
        ivCaricatureCover.setImageWidth(250);
        ivCaricatureCover.setImageHeight(350);
        ImageLoadUtils.glideLoading(mContext, "http://images.dmzj.com/", caricatureEntity.getCover(), ivCaricatureCover);
        tvCaricatureTitle.setText(caricatureEntity.getTitle());

        if(caricatureEntity.getIsCollect() == 1){
            btnCaricatureCollect.setText("已收藏");
        } else {
            btnCaricatureCollect.setText("收藏");
        }

        btnCaricatureCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchCaricatureListener != null) {
                    searchCaricatureListener.onClickCollect(position);
                }
            }
        });
    }
}
