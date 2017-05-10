package org.acg12.ui.activity;

import android.os.Bundle;
import android.view.View;

import org.acg12.config.Constant;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.ui.views.SkinView;

public class SkinActivity extends PresenterActivityImpl<SkinView> implements View.OnClickListener {

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        mView.bindData();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == Constant.TOOLBAR_ID){
            finish();
        }
    }
}
