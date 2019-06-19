package com.acg12.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.acg12.ui.base.SkinBaseActivity;
import com.acg12.ui.views.AboutView;

/**
 * Created by Administrator on 2017/5/10.
 */
public class AboutActivity extends SkinBaseActivity<AboutView> implements View.OnClickListener{

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        setTranslucentStatus();
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
