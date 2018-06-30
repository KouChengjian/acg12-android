package org.acg12.ui.views;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;

import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.widget.TipLayoutView;
import com.acg12.lib.widget.ToolBarView;

import org.acg12.R;

import butterknife.BindView;

/**
 * Created with IntelliJ IDEA.
 */
public class CalendarView extends ViewImpl {

    @BindView(R.id.toolBarView)
    ToolBarView mToolBarView;
    @BindView(R.id.tipLayoutView)
    TipLayoutView mTipLayoutView;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_calendar;
    }

    @Override
    public void created() {
        super.created();
        mToolBarView.setNavigationOrBreak("每日放送");
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter , mToolBarView.getToolbar());
    }

    public TipLayoutView getTipLayoutView(){
        return mTipLayoutView;
    }

    public void startProgress(){
        mTipLayoutView.startProgress();
    }

    public void stopProgress(){
        mCoordinatorLayout.setVisibility(View.VISIBLE);
        mTipLayoutView.stopProgress();
    }

    public void stopProgressOrNetError(){
        mTipLayoutView.stopProgressOrNetError();
    }
}
