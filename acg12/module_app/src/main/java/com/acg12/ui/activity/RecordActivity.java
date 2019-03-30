package com.acg12.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.acg12.R;
import com.acg12.lib.utils.skin.AttrFactory;
import com.acg12.lib.utils.skin.entity.DynamicAttr;
import com.acg12.ui.base.SkinBaseActivity;
import com.acg12.ui.views.RecordView;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends SkinBaseActivity<RecordView> implements View.OnClickListener {

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
    protected void onDestroy() {
        super.onDestroy();
    }
}
