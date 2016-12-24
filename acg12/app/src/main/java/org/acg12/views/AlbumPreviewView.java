package org.acg12.views;

import android.annotation.TargetApi;
import android.os.Build;
import android.widget.ImageView;

import org.acg12.R;
import org.acg12.bean.Album;
import org.acg12.ui.ViewImpl;
import org.acg12.utlis.ImageLoadUtils;

import butterknife.BindView;

/**
 * Created by DELL on 2016/12/24.
 */
public class AlbumPreviewView extends ViewImpl {

    @BindView(R.id.details_background_image)
    ImageView detailsBackgroundImage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_album_preview;
    }

    @Override
    public void created() {
        super.created();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void ss(String album , String url){
        detailsBackgroundImage.setTransitionName(album);
        ImageLoadUtils.universalLoading(url , detailsBackgroundImage);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
    }
}
