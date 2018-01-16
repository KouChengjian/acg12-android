package org.acg12.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.acg12.common.ui.base.BaseActivity;
import com.acg12.kk.listener.ParameCallBack;

import org.acg12.ui.views.SearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity<SearchView> implements View.OnClickListener , ParameCallBack {

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);

        List<String> strList = new ArrayList<>();
        strList.add("xixi");
        strList.add("xixi");
        strList.add("xixi");
        strList.add("xixi");
        strList.add("xixi");
        strList.add("xixi");
        strList.add("xixi");
        strList.add("xixi");
        mView.bindHistorySearch(strList);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
    }

    @Override
    public void onCall(Object object) {

    }
}
