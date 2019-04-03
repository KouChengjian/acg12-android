package com.acg12.lib.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.acg12.lib.R;
import com.acg12.lib.utils.PixelUtil;
import com.acg12.lib.utils.glide.GlideApp;
import com.acg12.lib.utils.glide.GlideRequest;
import com.acg12.lib.utils.glide.progress.ProgressInterceptor;
import com.acg12.lib.utils.glide.progress.ProgressListener;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with Android Studio.
 * User: KCJ
 * Date: 2019/4/2 14:32
 * Description:
 */
public class GlideProgressCaricatureImageView extends RelativeLayout {

    private Context context;
    private GlideProgressView glideProgressView;
    private ImageView imageView;
    private String url;
    private int module, tagWidth, tagHeight, screenWidth;
    private boolean hasAutoLoading = true;
    private OnResetLoadingImageListener onResetLoadingImageListener;

    public GlideProgressCaricatureImageView(Context context) {
        this(context, null);
    }

    public GlideProgressCaricatureImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews();
    }

    private void initViews() {
        imageView = new ImageView(context);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ViewGroup.LayoutParams imageLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(imageLayoutParams);
        addView(imageView);


        glideProgressView = new GlideProgressView(context);
        glideProgressView.setMax(100);
        glideProgressView.setProgressColor(0xffffffff);
        glideProgressView.setProgressWidth(PixelUtil.dp2px(context, 0));
        glideProgressView.setRoundColor(0xfff3f3f8);
        glideProgressView.setRoundWidth(PixelUtil.dp2px(context, 4));
        glideProgressView.setStartAngle(-90);
        glideProgressView.setStyle(1);
        RelativeLayout.LayoutParams progressLayoutParams = new RelativeLayout.LayoutParams(PixelUtil.dp2px(context, 50), PixelUtil.dp2px(context, 50));
        progressLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        progressLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        glideProgressView.setLayoutParams(progressLayoutParams);
        glideProgressView.setVisibility(View.VISIBLE);
        addView(glideProgressView);
    }

    public void setHasAutoLoading(boolean hasAutoLoading) {
        this.hasAutoLoading = hasAutoLoading;
    }

    public void setOnResetLoadingImageListener(OnResetLoadingImageListener onResetLoadingImageListener) {
        this.onResetLoadingImageListener = onResetLoadingImageListener;
    }

    public void loadUrl(final String url, int tagWidth, int tagHeight, final int screenWidth, final int module) {
        this.url = url;
        this.tagWidth = tagWidth;
        this.tagHeight = tagHeight;
        this.module = module;
        this.screenWidth = screenWidth;

        ProgressInterceptor.addListener(url, new ProgressListener() {
            @Override
            public void onProgress(int progress) {
                glideProgressView.setProgress(progress);
            }
        });

        final HashMap<String, String> header = new HashMap<>();
        header.put("Referer", "http://images.dmzj.com/");
        Headers headers = new Headers() {
            @Override
            public Map<String, String> getHeaders() {
                return header;
            }
        };
        GlideUrl gliderUrl = new GlideUrl(url, headers);
        GlideRequest<Bitmap> transition = GlideApp.with(context)
                .asBitmap()
                .override(tagWidth, tagHeight)
                .load(gliderUrl)
                .placeholder(new ColorDrawable(Color.BLACK))
                .error(R.mipmap.bg_load_image_reset)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        RequestListener<Bitmap> requestListener = new RequestListener<Bitmap>() {
            @Override
            public boolean onResourceReady(Bitmap bitmap, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                ProgressInterceptor.removeListener(url);
                glideProgressView.setVisibility(View.GONE);
                imageView.setOnClickListener(new OnClickSucceedListener());
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                float scale = ((float) height) / width;
                if (module == 0) {
//                    if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
//                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                    }
                    ViewGroup.LayoutParams layoutParams = getLayoutParams();
                    layoutParams.width = screenWidth;
                    layoutParams.height = (int) (scale * screenWidth);
                    setLayoutParams(layoutParams);
                } else {
                    RelativeLayout.LayoutParams progressLayoutParams = (RelativeLayout.LayoutParams)imageView.getLayoutParams();
                    progressLayoutParams.width = screenWidth;
                    progressLayoutParams.height = (int) (scale * screenWidth);
                    progressLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    progressLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                    imageView.setLayoutParams(progressLayoutParams);
                    setBackgroundResource(R.color.black);
                }
                return false;
            }

            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                ProgressInterceptor.removeListener(url);
                glideProgressView.setVisibility(View.GONE);
                imageView.setOnClickListener(new OnClickErrorListener());
                return false;
            }
        };
        transition.listener(requestListener).into(imageView);
    }

    public class OnClickErrorListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (hasAutoLoading) {
                loadUrl(url, tagWidth, tagHeight,screenWidth, module);
            }
            if (onResetLoadingImageListener != null) {
                onResetLoadingImageListener.onResetLoadingImage();
            }
        }
    }

    public class OnClickSucceedListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (onResetLoadingImageListener != null) {
                onResetLoadingImageListener.onClickImage();
            }
        }
    }

    public interface OnResetLoadingImageListener {
        void onResetLoadingImage();

        void onClickImage();
    }
}
