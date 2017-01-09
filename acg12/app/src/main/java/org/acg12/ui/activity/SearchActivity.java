package org.acg12.ui.activity;

import android.os.Bundle;
import android.view.View;

import org.acg12.config.Constant;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.views.SearchView;

public class SearchActivity extends PresenterActivityImpl<SearchView> implements View.OnClickListener{

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        String title = getIntent().getExtras().getString("title");
        mView.bindData(title);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == Constant.TOOLBAR_ID){
            finish();
        }
    }
}
