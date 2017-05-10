package org.acg12.ui.fragment;

import android.os.Bundle;
import android.view.View;

import org.acg12.config.Config;
import org.acg12.config.Constant;
import org.acg12.ui.base.PresenterFragmentImpl;
import org.acg12.ui.views.SkinView;

/**
 * Created by DELL on 2017/1/11.
 */
public class SkinFragment extends PresenterFragmentImpl<SkinView> implements
        View.OnClickListener{

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        mView.bindData();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == Constant.TOOLBAR_ID){
            Config.navigationEventBus().post(true);
        }
    }
}
