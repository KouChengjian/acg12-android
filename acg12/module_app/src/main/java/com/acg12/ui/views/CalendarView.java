package com.acg12.ui.views;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.acg12.entity.Calendar;
import com.acg12.lib.ui.adapter.CommonPagerAdapter;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.widget.TipLayoutView;
import com.acg12.lib.widget.ToolBarView;
import com.acg12.ui.fragment.CalendarTypeFragment;

import com.acg12.R;
import com.acg12.entity.Calendar;
import com.acg12.ui.fragment.CalendarTypeFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewpager;

    private Fragment[] fragments;
    private CommonPagerAdapter mCommonPagerAdapter;
    private final String[] titles = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};

    @Override
    public int getLayoutId() {
        return R.layout.fragment_calendar;
    }

    @Override
    public void created() {
        super.created();
        mToolBarView.setNavigationOrBreak(R.mipmap.ic_action_home, "每日放送");
        mTipLayoutView.setOnReloadClick((TipLayoutView.OnReloadClick) mPresenter);
    }


    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter, mToolBarView.getToolbar());
    }

    public ToolBarView getToolBarView(){
        return mToolBarView;
    }

    public void bindData(FragmentManager fragmentManager , List<Calendar> result) {
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        String curDate = dateFm.format(new Date());
        String[] s = new String[titles.length];
        int positopn = 0;
        for (int i = 0 ; i < titles.length ; i++){
            String s1 = titles[i];
            if(s1.equals(curDate)){
                positopn = i;
                break;
            }
        }
        for (int i = 0 ; i < titles.length - positopn ; i++){
            s[i] = titles[positopn+i];
        }
        for (int i = titles.length - positopn ,j = 0; i < titles.length ; i++){
            s[i] = titles[j];
            j++;
        }

        fragments = new Fragment[titles.length];
        for (int i = 0 ; i < s.length ; i++){
            for (int j = 0  , num = result.size(); j < num ; j++ ){
                Calendar calendar = result.get(j);
                if(s[i].equals(calendar.getCn())){
                    fragments[i] = CalendarTypeFragment.newInstance(calendar);
                    break;
                }
            }
        }
        mCommonPagerAdapter = new CommonPagerAdapter(((AppCompatActivity) getContext()).getSupportFragmentManager(), fragments, s);
        mViewpager.setAdapter(mCommonPagerAdapter);
        mViewpager.setOffscreenPageLimit(fragments.length);
        mTabLayout.setupWithViewPager(mViewpager);
    }

    public TipLayoutView getTipLayoutView() {
        return mTipLayoutView;
    }

    public void startProgress() {
        mTipLayoutView.showLoading();
    }

    public void stopProgress() {
        mCoordinatorLayout.setVisibility(View.VISIBLE);
        mTipLayoutView.showContent();
        mTipLayoutView.setVisibility(View.GONE);
//        mToolBarView.setVisibility(View.GONE);
        mTabLayout.setVisibility(View.VISIBLE);
        mViewpager.setVisibility(View.VISIBLE);
    }

    public void stopProgressOrNetError() {
        mTipLayoutView.showNetError();
    }
}
