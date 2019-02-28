package com.acg12.ui.activity;

import android.os.Bundle;

import com.acg12.ui.base.SkinBaseActivity;
import com.acg12.ui.views.CollectView;

public class CollectActivity extends SkinBaseActivity<CollectView> {
    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        setTranslucentStatus();
    }
}
