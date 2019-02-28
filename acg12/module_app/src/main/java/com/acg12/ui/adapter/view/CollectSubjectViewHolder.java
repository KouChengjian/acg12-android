package com.acg12.ui.adapter.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.acg12.R;
import com.acg12.entity.CollectSubjectEntity;
import com.acg12.lib.ui.adapter.CommonRecyclerViewHolder;
import com.acg12.lib.utils.glide.ImageLoadUtils;
import com.acg12.lib.widget.BGButton;
import com.acg12.ui.adapter.CollectSubjectAdapter;
import com.acg12.widget.LabelImageView;

import java.util.List;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/2/28 17:37
 * Description:
 */
public class CollectSubjectViewHolder extends CommonRecyclerViewHolder {

    LabelImageView ivCollectSubjectCover;
    TextView tvCollectSubjectTitle;
    BGButton btnCollectSubjectCover;

    private CollectSubjectAdapter.CollectSubjectListener collectSubjectListener;

    public CollectSubjectViewHolder(View itemView) {
        super(itemView);
        ivCollectSubjectCover = itemView.findViewById(R.id.iv_collect_subject_cover);
        tvCollectSubjectTitle = itemView.findViewById(R.id.tv_collect_subject_title);
        btnCollectSubjectCover = itemView.findViewById(R.id.btn_collect_subject_cover);
    }

    public void setCollectSubjectListener(CollectSubjectAdapter.CollectSubjectListener collectSubjectListener) {
        this.collectSubjectListener = collectSubjectListener;
    }

    @Override
    public void bindData(Context mContext, List list, final int position) {
        CollectSubjectEntity collectSubjectEntity = (CollectSubjectEntity) list.get(position);
        tvCollectSubjectTitle.setText(TextUtils.isEmpty(collectSubjectEntity.getNameCn()) ? collectSubjectEntity.getName() : collectSubjectEntity.getNameCn());
        ImageLoadUtils.glideLoading(mContext, collectSubjectEntity.getImage(), ivCollectSubjectCover);
        ivCollectSubjectCover.setTextContent(collectSubjectEntity.getTypeName());
        ivCollectSubjectCover.setLabelBackGroundColor(mContext.getResources().getColor(R.color.theme_body));
        ivCollectSubjectCover.setLabelVisable(true);
        if (collectSubjectEntity.getIsCollect() == 1) {
            btnCollectSubjectCover.setText("已收藏");
        } else {
            btnCollectSubjectCover.setText("收藏");
        }
        btnCollectSubjectCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectSubjectListener != null) {
                    collectSubjectListener.onClickCollect(position);
                }
            }
        });
    }
}
