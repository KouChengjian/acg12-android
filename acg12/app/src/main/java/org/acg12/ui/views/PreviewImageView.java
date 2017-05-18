package org.acg12.ui.views;

import android.support.v7.widget.Toolbar;

import org.acg12.R;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.base.PresenterHelper;
import org.acg12.utlis.ImageLoadUtils;
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
        ImageLoadUtils.universalLoading(url , dragPhotoView);
    }
}
