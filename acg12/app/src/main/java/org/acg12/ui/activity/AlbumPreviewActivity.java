package org.acg12.ui.activity;

import android.os.Bundle;

import org.acg12.bean.Album;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.views.AlbumPreviewView;

public class AlbumPreviewActivity extends PresenterActivityImpl<AlbumPreviewView> {

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);

        String album = getIntent().getExtras().getString("transitionName");
        String url = getIntent().getExtras().getString("imageUrl");
        mView.ss(album , url);


    }
}
