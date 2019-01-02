package com.acg12.ui.views;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.acg12.R;
import com.acg12.constant.Constant;
import com.acg12.entity.CaricatureChaptersEntity;
import com.acg12.entity.CaricatureEntity;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.utils.PreferencesUtils;
import com.acg12.lib.utils.ScreenUtils;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.widget.ToolBarView;
import com.acg12.ui.adapter.CaricatureInfoAdapter;
import com.acg12.widget.caricature.TouchRecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2018/12/30 18:45
 * Description:
 */
public class CaricatureInfoView extends ViewImpl {


    @BindView(R.id.fl_layout)
    protected FrameLayout flLayout;
    @BindView(R.id.toolBarView)
    ToolBarView toolBarView;
    @BindView(R.id.touchRecyclerView)
    TouchRecyclerView touchRecyclerView;
    @BindView(R.id.tv_curr_pager)
    TextView tvCurrPager;
    @BindView(R.id.ll_bottom)
    protected LinearLayout bottomLinearLayout;
    @BindView(R.id.bottom_deekBar)
    SeekBar bottomDeekBar;
    @BindView(R.id.tv_bottom_menu)
    TextView tvBottomMenu;
    @BindView(R.id.tv_bottom_brightness)
    TextView tvBottomBrightness;
    @BindView(R.id.tv_bottom_switch_screen)
    TextView tvBottomSwitchScreen;
    @BindView(R.id.tv_bottom_switch_module)
    TextView tvBottomSwitchModule;
    @BindView(R.id.ll_left_layout)
    protected LinearLayout leftLayout;
    @BindView(R.id.tv_left_title)
    TextView tvLeftTitle;
    @BindView(R.id.rv_left_list)
    RecyclerView rvLeftList;


    //RecyclerView帮助类。主要是变成Viewpager的模式需要
    PagerSnapHelper mPagerSnapHelper;
    CaricatureInfoAdapter mCaricatureInfoAdapter;
    RecyclerViewPreloader<CaricatureChaptersEntity> mRecyclerViewPreLoader;

    @Override
    public int getLayoutId() {
        return R.layout.activity_caricature_info;
    }

    @Override
    public void created() {
        super.created();
        bottomLinearLayout.setTranslationY(ViewUtil.getViewMeasuredHeight(bottomLinearLayout));
        toolBarView.setTranslationY(-ViewUtil.getViewMeasuredHeight(toolBarView));
        leftLayout.setTranslationX(-ScreenUtils.getScreenWidth(getContext()));

        final int module = PreferencesUtils.getInt(getContext(), Constant.XML_KEY_CARICATURE_MODE, 0);
        int layoutRes = module == 0 ? R.layout.item_caricature_vertical : R.layout.item_caricature_land;
        resetModule(module);
        final ViewPreloadSizeProvider<CaricatureChaptersEntity> preloadSizeProvider = new ViewPreloadSizeProvider<>();
        mCaricatureInfoAdapter = new CaricatureInfoAdapter(getContext(), layoutRes, preloadSizeProvider);
        if (mRecyclerViewPreLoader != null) {
            touchRecyclerView.removeOnScrollListener(mRecyclerViewPreLoader);
        }
        mRecyclerViewPreLoader = new RecyclerViewPreloader<>(Glide.with(getContext()), mCaricatureInfoAdapter, preloadSizeProvider, Constant.MAX_PRELOAD);
        touchRecyclerView.addOnScrollListener(mRecyclerViewPreLoader);
        touchRecyclerView.setAdapter(mCaricatureInfoAdapter);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
    }


    public void bindData(CaricatureEntity caricatureEntity) {
        toolBarView.setNavigationTitle(caricatureEntity.getTitle());
    }

    /**
     * 初始化观看模式
     *
     * @param module 模式
     */
    private void resetModule(int module) {
        // PreCacheLayoutManager linearLayoutManager = new PreCacheLayoutManager(this);
        //提前加载
        // linearLayoutManager.setExtraSpace(2);
        //默认的模式
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        if (module == 0) {
            touchRecyclerView.setLayoutManager(linearLayoutManager);
            if (mPagerSnapHelper != null) mPagerSnapHelper.attachToRecyclerView(null);
        } else {
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            touchRecyclerView.setLayoutManager(linearLayoutManager);
            mPagerSnapHelper = new PagerSnapHelper();
            mPagerSnapHelper.attachToRecyclerView(touchRecyclerView);
        }
        mCaricatureInfoAdapter = null;
    }
}
