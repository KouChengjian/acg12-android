package org.acg12.ui.views;

import android.support.v4.widget.SwipeRefreshLayout;

import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.widget.recycle.CommonRecycleview;

import org.acg12.R;
import org.acg12.entity.Calendar;
import org.acg12.ui.adapter.CalendarTypeAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2018/7/3 10:35
 * Description:
 */
public class CalendarTypeView extends ViewImpl {

    @BindView(R.id.commonRecycleview)
    CommonRecycleview mCommonRecycleview;
    CalendarTypeAdapter mCalendarTypeAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_calendar_type;
    }

    @Override
    public void created() {
        super.created();
        mCommonRecycleview.setStaggeredGridLayoutManager();
        mCalendarTypeAdapter = new CalendarTypeAdapter(getContext());
        mCommonRecycleview.setAdapter(mCalendarTypeAdapter);
        mCommonRecycleview.startRefreshing();
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        mCommonRecycleview.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) mPresenter);
        mCommonRecycleview.setOnItemClickListener((ItemClickSupport.OnItemClickListener)mPresenter);
    }

    public void bindData(List<Calendar> result , boolean refresh){
        if (refresh) {
            mCalendarTypeAdapter.setList(result);
            mCommonRecycleview.notifyChanged();
        } else {
            mCalendarTypeAdapter.addAll(result);
            mCommonRecycleview.notifyChanged(mCalendarTypeAdapter.getList().size() - result.size() , mCalendarTypeAdapter.getList().size());
        }
    }

    public void stopLoading(){
        mCommonRecycleview.stopLoading();
    }

    public void stopRefreshLoadMore(boolean refresh) {
        mCommonRecycleview.stopRefreshLoadMore(refresh);
    }

    public Calendar getObject(int position){
        return mCalendarTypeAdapter.getList().get(position);
    }
}
