package com.acg12.ui.activity;

import android.os.Bundle;

import com.acg12.ui.base.SkinBaseActivity;
import com.acg12.ui.views.WebviewView;

public class WebviewActivity extends SkinBaseActivity<WebviewView> {

    private String url;

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        setTranslucentStatus();
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        url = getIntent().getStringExtra("url");
        mView.paddingData(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
