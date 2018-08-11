package org.acg12.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.fragment.PresenterFragmentImpl;

import org.acg12.entity.Calendar;
import org.acg12.ui.activity.SearchInfoActivity;
import org.acg12.ui.views.CalendarTypeView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarTypeFragment extends PresenterFragmentImpl<CalendarTypeView> implements SwipeRefreshLayout.OnRefreshListener, ItemClickSupport.OnItemClickListener {

    private Calendar mCalendar;

    public static CalendarTypeFragment newInstance(Calendar calendar) {
        CalendarTypeFragment fragment = new CalendarTypeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("calendar", calendar);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        mCalendar = (Calendar) getArguments().getSerializable("calendar");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        }, 1 * 1000);
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        Calendar calendar = mView.getObject(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", calendar.getsId());
        bundle.putInt("type", 0);
        bundle.putString("title", calendar.getName_cn().isEmpty() ? calendar.getName() : calendar.getName_cn());
        startAnimActivity(SearchInfoActivity.class, bundle);
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    public void refresh() {
        if (mCalendar == null) {
            ShowToastView("加载失败");
            mView.stopRefreshLoadMore(true);
            return;
        }
        mView.bindData(mCalendar.getCalendarList(), true);
        mView.stopRefreshLoadMore(true);
    }
}
