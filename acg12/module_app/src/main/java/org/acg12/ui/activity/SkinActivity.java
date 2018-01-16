package org.acg12.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.acg12.common.ui.base.BaseActivity;

import org.acg12.ui.views.SkinView;

public class SkinActivity extends BaseActivity<SkinView> implements View.OnClickListener {

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        mView.bindData();
    }

}
