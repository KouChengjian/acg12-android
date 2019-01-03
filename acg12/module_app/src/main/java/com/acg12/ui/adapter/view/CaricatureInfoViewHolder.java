package com.acg12.ui.adapter.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.acg12.R;
import com.acg12.entity.CaricatureChaptersEntity;
import com.acg12.entity.CaricatureChaptersPageEntity;
import com.acg12.lib.ui.adapter.CommonRecyclerViewHolder;
import com.acg12.lib.utils.glide.GlideApp;
import com.acg12.lib.utils.glide.GlideRequest;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import java.util.List;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/1/2 16:23
 * Description:
 */
public class CaricatureInfoViewHolder extends CommonRecyclerViewHolder {

//    public CaricatureInfoViewHolder(Context context, ViewGroup viewGroup) {
//        this(getItemView(context, R.layout.item_caricature_vertical, viewGroup));
//        this(LayoutInflater.from(context).inflate(R.layout.item_caricature_vertical, viewGroup, false));
//    }

    private ImageView imageView;

    //加载图片的宽度
    private int tagWidth;
    //加载图片的高度
    private int tagHeight;
    private int screenWidth;
    private ViewPreloadSizeProvider<CaricatureChaptersPageEntity> preloadSizeProvider;

    public CaricatureInfoViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.iv_cover);
    }

    public void setWidthOrHeight(int tagWidth, int tagHeight, int screenWidth) {
        this.tagWidth = tagWidth;
        this.tagHeight = tagHeight;
        this.screenWidth = screenWidth;
    }

    public void setPreloadSizeProvider(ViewPreloadSizeProvider<CaricatureChaptersPageEntity> preloadSizeProvider) {
        this.preloadSizeProvider = preloadSizeProvider;
    }

    public void bindData(Context mContext, CaricatureChaptersEntity chaptersEntity, List list, int position, int module) {
        super.bindData(mContext, list, position);
        CaricatureChaptersPageEntity chaptersPage = (CaricatureChaptersPageEntity) list.get(position);
        if (chaptersPage.getIndex() == -1) chaptersPage.setIndex(chaptersEntity.getIndex());
        GlideRequest<Bitmap> transition = GlideApp.with(mContext)
                .asBitmap()
                .override(tagWidth, tagHeight)
                .load(chaptersPage.getUrl())
                .placeholder(new ColorDrawable(Color.BLACK))
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        if (module == 0) {
            RequestListener<Bitmap> requestListener = new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap bitmap, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    if (imageView == null) {
                        return false;
                    }

                    if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                   /* ViewGroup.LayoutParams params = imageView.getLayoutParams();
                    int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                    float scale = (float) vw / (float) resource.getWidth();
                    int vh = Math.round(resource.getHeight() * scale);
                    params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                    imageView.setLayoutParams(params);*/

                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    float scale = ((float) height) / width;
                    ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                    layoutParams.width = screenWidth;
                    layoutParams.height = (int) (scale * screenWidth);
                    imageView.setLayoutParams(layoutParams);

                    return false;
                }
            };
            transition.listener(requestListener).into(imageView);
        } else {
            transition.into(imageView);
        }
        preloadSizeProvider.setView(imageView);
    }
}
