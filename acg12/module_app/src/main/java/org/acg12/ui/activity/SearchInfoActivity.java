package org.acg12.ui.activity;

import android.os.Bundle;

import org.acg12.ui.base.SkinBaseActivity;
import org.acg12.ui.views.SearchInfoView;

public class SearchInfoActivity extends SkinBaseActivity<SearchInfoView> {

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        String title = getIntent().getExtras().getString("title");
        mView.bindData(title);

//        List<DynamicAttr> mDynamicAttr = new ArrayList<DynamicAttr>();
//        mDynamicAttr.add(new DynamicAttr(AttrFactory.TABLAYOUT, R.color.theme_primary));
//        dynamicAddView(mView.getTabLayout(), mDynamicAttr);


    }

}
