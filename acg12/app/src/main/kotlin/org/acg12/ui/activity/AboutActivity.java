package org.acg12.ui.activity;

import android.os.Bundle;
import android.view.View;

import org.acg12.conf.Constant;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.ui.view.AboutView;

/**
 * Created by Administrator on 2017/5/10.
 */
public class AboutActivity extends PresenterActivityImpl<AboutView> implements View.OnClickListener{

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == Constant.TOOLBAR_ID) {
            finish();
        }
    }
}
