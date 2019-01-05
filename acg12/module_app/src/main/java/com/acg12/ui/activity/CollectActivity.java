package com.acg12.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.acg12.constant.Constant;
import com.acg12.ui.base.SkinBaseActivity;
import com.acg12.ui.views.CollectView;

public class CollectActivity extends SkinBaseActivity<CollectView> implements View.OnClickListener {

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
//        Config.initListVideoUtil(mContext);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if(id == Constant.TOOLBAR_ID){
            aminFinish();
        }
    }
}
