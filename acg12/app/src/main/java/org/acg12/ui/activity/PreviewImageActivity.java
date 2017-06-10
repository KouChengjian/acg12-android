package org.acg12.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import org.acg12.conf.Constant;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.ui.views.PreviewImageView;

public class PreviewImageActivity extends PresenterActivityImpl<PreviewImageView> implements View.OnClickListener {

    String url;

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        replace = false;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        url = getIntent().getStringExtra("url");
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        mView.loaderImage(url);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == Constant.TOOLBAR_ID) {
            finish();
        }
    }
}
