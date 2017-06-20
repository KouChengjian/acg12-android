package org.acg12.ui.activity;

import android.os.Bundle;
import android.view.View;


import org.acg12.R;
import org.acg12.conf.Constant;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.utlis.skin.entity.AttrFactory;
import org.acg12.utlis.skin.entity.DynamicAttr;
import org.acg12.ui.views.SearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends PresenterActivityImpl<SearchView> implements View.OnClickListener{

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        String title = getIntent().getExtras().getString("title");
        mView.bindData(title);

        List<DynamicAttr> mDynamicAttr = new ArrayList<DynamicAttr>();
        mDynamicAttr.add(new DynamicAttr(AttrFactory.TABLAYOUT, R.color.theme_primary));
        dynamicAddView(mView.getTabLayout(), mDynamicAttr);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == Constant.TOOLBAR_ID){
            aminFinish();
        }
    }
}
