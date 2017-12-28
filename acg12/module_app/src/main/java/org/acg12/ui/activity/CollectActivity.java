package org.acg12.ui.activity;

import android.os.Bundle;
import android.view.View;

import org.acg12.conf.Config;
import org.acg12.conf.Constant;
import org.acg12.ui.base.BaseActivity;
import org.acg12.ui.views.CollectView;

public class CollectActivity extends BaseActivity<CollectView> implements View.OnClickListener {

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        Config.initListVideoUtil(mContext);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == Constant.TOOLBAR_ID){
            aminFinish();
        }
    }
}
