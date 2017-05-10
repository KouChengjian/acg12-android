package org.acg12.ui.activity;

import android.os.Bundle;
import android.view.View;

import org.acg12.config.Constant;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.ui.views.AboutView;


/**
 * Created by Administrator on 2017/5/10.
 */
public class AboutActivity extends PresenterActivityImpl<AboutView> implements View.OnClickListener{

    @Override
    public void created(Bundle savedInstance) {
        super.create(savedInstance);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == Constant.TOOLBAR_ID){
            finish();
        }
    }
}
