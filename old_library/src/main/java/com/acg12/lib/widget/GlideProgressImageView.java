package com.acg12.lib.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.acg12.lib.R;
import com.acg12.lib.utils.PixelUtil;
import com.acg12.lib.utils.glide.progress.ProgressInterceptor;
import com.acg12.lib.utils.glide.progress.ProgressListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

/**
 * Created with Android Studio.
 * User: kcj
 * Date: 2019/4/1 20:26
 * Description:
 */
public class GlideProgressImageView extends RelativeLayout {

    private Context context;
    private GlideProgressView glideProgressView;
    private ImageView imageView;
    private String url;
    private boolean hasAutoLoading = true;
    private OnResetLoadingImageListener onResetLoadingImageListener;

    public GlideProgressImageView(Context context) {
        this(context, null);
    }

    public GlideProgressImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initViews();
    }

    private void initViews() {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ViewGroup.LayoutParams imageLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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

    public void loadUrl(final String url) {
        this.url = url;
        ProgressInterceptor.addListener(url, new ProgressListener() {
            @Override
            public void onProgress(int progress) {
                glideProgressView.setProgress(progress);
            }
        });

        RequestOptions options = new RequestOptions()
//            .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.bg_load_image_reset);

        Glide.with(this)
                .load(url)
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        ProgressInterceptor.removeListener(url);
                        glideProgressView.setVisibility(View.GONE);
                        imageView.setOnClickListener(new OnClickErrorListener());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        ProgressInterceptor.removeListener(url);
                        glideProgressView.setVisibility(View.GONE);
                        imageView.setOnClickListener(null);
                        return false;
                    }
                })
                .into(imageView);

    }

    public class OnClickErrorListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            if (hasAutoLoading) {
                loadUrl(url);
            }
            if (onResetLoadingImageListener != null) {
                onResetLoadingImageListener.onResetLoadingImage();
            }
        }
    }

    public interface OnResetLoadingImageListener {
        void onResetLoadingImage();
    }
}
