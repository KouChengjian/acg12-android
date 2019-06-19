package com.acg12.ui.adapter.view;

import android.content.Context;
import android.view.View;

import com.acg12.R;
import com.acg12.entity.CaricatureChaptersEntity;
import com.acg12.entity.CaricatureChaptersPageEntity;
import com.acg12.lib.ui.adapter.CommonRecyclerViewHolder;
import com.acg12.lib.widget.GlideProgressCaricatureImageView;
import com.acg12.ui.adapter.CaricatureInfoAdapter;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import java.util.List;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/1/2 16:23
 * Description:
 */
public class CaricatureInfoViewHolder extends CommonRecyclerViewHolder {

    //    private ImageView imageView;
    private GlideProgressCaricatureImageView glideProgressCaricatureImageView;

    private int tagWidth;
    private int tagHeight;
    private int screenWidth;
    private ViewPreloadSizeProvider<CaricatureChaptersPageEntity> preloadSizeProvider;
    private CaricatureInfoAdapter.OnCaricatureInfoListener onCaricatureInfoListener;

    public CaricatureInfoViewHolder(View itemView) {
        super(itemView);
        glideProgressCaricatureImageView = itemView.findViewById(R.id.glideProgressCaricatureImageView);
    }

    public void setWidthOrHeight(int tagWidth, int tagHeight, int screenWidth) {
        this.tagWidth = tagWidth;
        this.tagHeight = tagHeight;
        this.screenWidth = screenWidth;
    }

    public void setPreloadSizeProvider(ViewPreloadSizeProvider<CaricatureChaptersPageEntity> preloadSizeProvider) {
        this.preloadSizeProvider = preloadSizeProvider;
    }

    public void setOnCaricatureInfoListener(CaricatureInfoAdapter.OnCaricatureInfoListener onCaricatureInfoListener) {
        this.onCaricatureInfoListener = onCaricatureInfoListener;
    }

    public void bindData(Context mContext, CaricatureChaptersEntity chaptersEntity, List list, final int position, int module) {
        super.bindData(mContext, list, position);
        CaricatureChaptersPageEntity chaptersPage = (CaricatureChaptersPageEntity) list.get(position);
        if (chaptersPage.getIndex() == -1)
            chaptersPage.setIndex(chaptersEntity.getIndex());
        glideProgressCaricatureImageView.loadUrl(chaptersPage.getUrl(), tagWidth, tagHeight, screenWidth, module);
        glideProgressCaricatureImageView.setOnResetLoadingImageListener(new GlideProgressCaricatureImageView.OnResetLoadingImageListener() {
            @Override
            public void onClickImage() {
                if (onCaricatureInfoListener != null) {
                    onCaricatureInfoListener.onClickItem(position);
                }
            }

            @Override
            public void onResetLoadingImage() {

            }
        });
        preloadSizeProvider.setView(glideProgressCaricatureImageView);
    }
}
