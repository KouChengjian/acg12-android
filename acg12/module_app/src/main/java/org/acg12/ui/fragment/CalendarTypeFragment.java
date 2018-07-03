package org.acg12.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.fragment.PresenterFragmentImpl;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.widget.IRecycleView;

import org.acg12.R;
import org.acg12.conf.Constant;
import org.acg12.entity.Calendar;
import org.acg12.entity.Video;
import org.acg12.net.impl.HttpRequestImpl;
import org.acg12.ui.activity.PreviewBangumiActivity;
import org.acg12.ui.views.CalendarTypeView;

import java.util.List;

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
//        Bundle bundle = new Bundle();
//        bundle.putString("bangumiId" , mView.getBangumiId(position));
//        startAnimActivity(PreviewBangumiActivity.class , bundle);
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
