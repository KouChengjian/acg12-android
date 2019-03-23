package com.acg12.ui.adapter.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.acg12.R;
import com.acg12.entity.Album;
import com.acg12.lib.ui.adapter.CommonRecyclerViewHolder;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.utils.glide.ImageLoadUtils;
import com.acg12.ui.activity.PreviewImageActivity;
import com.acg12.widget.MyImageView;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/3/23 10:23
 * Description:
 */
public class NewestAlbumInfoViewHolder extends CommonRecyclerViewHolder {

    ProgressBar spinner;
    MyImageView dragPhotoView;
    TextView pageText;

    public NewestAlbumInfoViewHolder(View itemView) {
        super(itemView);
         spinner =  itemView.findViewById(R.id.page_loading);
         dragPhotoView =  itemView.findViewById(R.id.page_image);
         pageText =  itemView.findViewById(R.id.page_text);
    }

    @Override
    public void bindData(Context context, List list, int position) {
        super.bindData(context, list, position);
        Album album = (Album)list.get(position);
        ViewUtil.setText(pageText, album.getContent());
        spinner.setVisibility(View.GONE);
        dragPhotoView.setVisibility(View.VISIBLE);
        ImageLoadUtils.glideLoading(context, album.getImageUrl(), new DrawableImageViewTarget(dragPhotoView) {

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                super.onLoadCleared(placeholder);
                spinner.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onResourceReady(Drawable resource, @Nullable Transition<? super Drawable> transition) {
                super.onResourceReady(resource, transition);
                spinner.setVisibility(View.GONE);
                dragPhotoView.setVisibility(View.VISIBLE);
            }
        });
//
//        dragPhotoView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Bundle bundle = new Bundle();
//                bundle.putString("url", album.getImageUrl());
//                ViewUtil.startAnimActivity(((Activity) getContext()), PreviewImageActivity.class, bundle, 1000);
//            }
//        });
    }
}
