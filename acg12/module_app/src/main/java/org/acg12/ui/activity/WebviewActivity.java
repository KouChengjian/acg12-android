package org.acg12.ui.activity;

import android.os.Bundle;

import org.acg12.ui.base.BaseActivity;
import org.acg12.ui.views.WebviewView;

public class WebviewActivity extends BaseActivity<WebviewView> {

    private String url;

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        url = getIntent().getStringExtra("url");
        mView.paddingData(url);
    }

}
