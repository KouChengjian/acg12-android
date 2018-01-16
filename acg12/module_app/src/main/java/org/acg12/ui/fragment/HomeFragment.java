package org.acg12.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.acg12.common.ui.base.BaseFragment;

import org.acg12.R;
import org.acg12.ui.activity.SearchActivity;
import org.acg12.ui.views.HomeView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment<HomeView> implements SwipeRefreshLayout.OnRefreshListener ,View.OnClickListener{

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        refresh();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_home_search){
            startAnimActivity(SearchActivity.class);
        }
    }

    @Override
    public void onRefresh() {

    }

    public void refresh(){
        List<Object > objectList = new ArrayList<>();
        for (int i = 0 ; i < 10 ; i++){
            objectList.add(new Object());
        }
        mView.addObjectList(objectList);
    }


}
