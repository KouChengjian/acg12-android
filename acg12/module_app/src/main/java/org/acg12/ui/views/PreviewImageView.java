package org.acg12.ui.views;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.utils.glide.ImageLoadUtils;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import org.acg12.R;
import org.acg12.widget.dargphoto.PhotoView;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/17.
 */
public class PreviewImageView extends ViewImpl {

    @BindView(R.id.preview_image_toolbar)
    Toolbar preview_image_toolbar;
    @BindView(R.id.page_image)
    PhotoView dragPhotoView;
    @BindView(R.id.page_loading)
    ProgressBar page_loading;

    @Override
    public int getLayoutId() {
        return R.layout.activity_preview_image;
    }

    @Override
    public void created() {
        super.created();
        preview_image_toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        preview_image_toolbar.setTitle("");
        dragPhotoView.setBackgroundColor(0xff000000);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter ,preview_image_toolbar);
    }

    public void loaderImage(String url){
        url = url.replace("_fw658" , "");
        LogUtil.e(url+"====");
        ImageLoadUtils.glideLoading(getContext() , url , new GlideDrawableImageViewTarget(dragPhotoView){

            @Override
            public void onLoadStarted(Drawable placeholder) {
                page_loading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                page_loading.setVisibility(View.GONE);
            }

            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                super.onResourceReady(resource, glideAnimation);
                page_loading.setVisibility(View.GONE);
                dragPhotoView.setVisibility(View.VISIBLE);
            }
        });
    }
}
