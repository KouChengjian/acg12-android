package com.acg12.ui.views;

import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.acg12.R;
import com.acg12.lib.constant.Constant;
import com.acg12.entity.CaricatureChaptersEntity;
import com.acg12.entity.CaricatureChaptersPageEntity;
import com.acg12.entity.CaricatureEntity;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.utils.PreferencesUtils;
import com.acg12.lib.utils.ScreenUtils;
import com.acg12.lib.utils.Toastor;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.widget.TipLayoutView;
import com.acg12.lib.widget.ToolBarView;
import com.acg12.ui.activity.CaricatureInfoActivity;
import com.acg12.ui.adapter.CaricatureChapterAdapter;
import com.acg12.ui.adapter.CaricatureInfoAdapter;
import com.acg12.widget.caricature.TouchRecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader;
import com.bumptech.glide.util.ViewPreloadSizeProvider;

import java.util.List;

import butterknife.BindView;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

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
    TextView mTvCurrPager;
    @BindView(R.id.ll_bottom)
    protected LinearLayout mBottomLinearLayout;
    @BindView(R.id.bottom_deekBar)
    SeekBar mSeekBar;
    @BindView(R.id.tv_bottom_menu)
    TextView tvBottomMenu;
    @BindView(R.id.tv_bottom_brightness)
    TextView tvBottomBrightness;
    @BindView(R.id.tv_bottom_switch_screen)
    TextView tvBottomSwitchScreen;
    @BindView(R.id.tv_bottom_switch_module)
    TextView tvBottomSwitchModule;
    @BindView(R.id.ll_left_layout)
    protected LinearLayout mLeftLinearLayout;
    @BindView(R.id.tv_left_title)
    TextView tvLeftTitle;
    @BindView(R.id.rv_left_list)
    RecyclerView rvLeftList;
    @BindView(R.id.tipLayoutView)
    TipLayoutView mTipLayoutView;

    private CaricatureChaptersEntity mCaricatureChaptersEntity;
    private PagerSnapHelper mPagerSnapHelper; // RecyclerView帮助类。主要是变成Viewpager的模式需要
    private CaricatureInfoAdapter mCaricatureInfoAdapter;
    private CaricatureChapterAdapter mCaricatureChapterAdapter;
    private RecyclerViewPreloader<CaricatureChaptersPageEntity> mRecyclerViewPreLoader;

    @Override
    public int getLayoutId() {
        return R.layout.activity_caricature_info;
    }

    @Override
    public void created() {
        super.created();
        mBottomLinearLayout.setTranslationY(ViewUtil.getViewMeasuredHeight(mBottomLinearLayout));
        mLeftLinearLayout.setTranslationX(-ScreenUtils.getScreenWidth(getContext()));

        initPreLoaderAdapter();
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter, toolBarView.getToolbar(), tvBottomMenu, tvBottomBrightness, tvBottomSwitchScreen, tvBottomSwitchModule);
        mTipLayoutView.setOnReloadClick((TipLayoutView.OnReloadClick) mPresenter);
        touchRecyclerView.setITouchCallBack((TouchRecyclerView.ITouchCallBack) mPresenter);
        touchRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                List<CaricatureChaptersEntity> data = mCaricatureChapterAdapter.getList();
                if (data.size() == 0) {
                    return;
                }
                //当屏幕停止滚动，
                if (newState == SCROLL_STATE_IDLE) {
                    //更新当前对象在更新信息
                    updateCurrIndex(updateCurrObject(recyclerView));
                    if (isVisBottom(touchRecyclerView)) {
                        int lastPosition = mCaricatureChapterAdapter.getLastPosition();
                        int nextPosition = lastPosition + 1;

                        if ((nextPosition == data.size())) {
                            Toastor.ShowToast("后面已经没有内容了");
                            return;
                        }
                        CaricatureChaptersEntity chaptersEntity = mCaricatureChapterAdapter.getObject(nextPosition);
                        ((CaricatureInfoActivity) getContext()).onLoadMoreRequested(chaptersEntity.getIndex());
                    }
                }
            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress() <= 0 ? 0 : seekBar.getProgress() - 1;
                CaricatureChaptersPageEntity pagesBean = mCaricatureChaptersEntity.getPags().get(progress);
                if (pagesBean != null) {
                    int i = mCaricatureInfoAdapter.getList().indexOf(pagesBean);
                    if (i != -1) {
                        touchRecyclerView.scrollToPosition(i);
                        //先更新当前图片所在的对象
                        updateCurrIndex(updateCurrObject(i));
                    }
                }
            }
        });
    }

    public TipLayoutView getTipLayoutView() {
        return mTipLayoutView;
    }

    public void setTitle(String title){
        toolBarView.setNavigationOrBreak(title);
    }

    public void initPreLoaderAdapter() {
        final int module = PreferencesUtils.getInt(getContext(), Constant.XML_KEY_CARICATURE_MODE, 0);
        int layoutRes = module == 0 ? R.layout.item_caricature_vertical : R.layout.item_caricature_land;
        resetModule(module);
        final ViewPreloadSizeProvider<CaricatureChaptersPageEntity> preloadSizeProvider = new ViewPreloadSizeProvider<>();
        mCaricatureInfoAdapter = new CaricatureInfoAdapter(getContext(), layoutRes, preloadSizeProvider);
        mCaricatureInfoAdapter.setOnCaricatureInfoListener((CaricatureInfoAdapter.OnCaricatureInfoListener)mPresenter);
        if (mRecyclerViewPreLoader != null) {
            touchRecyclerView.removeOnScrollListener(mRecyclerViewPreLoader);
        }
        mRecyclerViewPreLoader = new RecyclerViewPreloader<>(Glide.with(getContext()), mCaricatureInfoAdapter, preloadSizeProvider, Constant.MAX_PRELOAD);
        touchRecyclerView.addOnScrollListener(mRecyclerViewPreLoader);
        touchRecyclerView.setAdapter(mCaricatureInfoAdapter);
        //为了更好的提高滚动的流畅性，可以加大 RecyclerView 的缓存，用空间换时间
        touchRecyclerView.setHasFixedSize(true);
        //touchRecyclerView.setItemViewCacheSize(10);
        touchRecyclerView.setDrawingCacheEnabled(true);
        touchRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }

    public void bindCaricatureData(CaricatureEntity caricatureEntity) {
        setTitle(caricatureEntity.getTitle());
        toolBarView.setTranslationY(-ViewUtil.getViewMeasuredHeight(toolBarView));

        mCaricatureChapterAdapter = new CaricatureChapterAdapter(getContext());
//        mCaricatureChapterAdapter.setIndex();
        mCaricatureChapterAdapter.setList(caricatureEntity.getChaptersList());
        mCaricatureChapterAdapter.setOnCaricatureChapterListener((CaricatureChapterAdapter.OnCaricatureChapterListener) mPresenter);
        rvLeftList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvLeftList.setAdapter(mCaricatureChapterAdapter);
        if (caricatureEntity.getChaptersList().size() == 0) {
            mTipLayoutView.showEmptyOrRefresh();
        } else {
            mTipLayoutView.showContent();
        }
    }

    public void bindChaptersData(CaricatureChaptersEntity chaptersEntity, int index, boolean refresh) {
        mCaricatureChaptersEntity = chaptersEntity;
        mCaricatureChapterAdapter.updatePosition(index);
        List<CaricatureChaptersPageEntity> pages = chaptersEntity.getPags();
        mCaricatureInfoAdapter.setComicPreView(chaptersEntity);
        if (refresh) {
            mCaricatureInfoAdapter.setList(pages);
            mCaricatureInfoAdapter.notifyDataSetChanged();

            rvLeftList.scrollToPosition(0);
            //更新数据
            updateCurrIndex(0);
        } else {
            mCaricatureInfoAdapter.addAll(pages);
            mCaricatureInfoAdapter.notifyDataSetChanged();
//            mCaricatureInfoAdapter.notifyItemChanged(mCaricatureInfoAdapter.getList().size() - pages.size(), mCaricatureInfoAdapter.getList().size());
//            mCaricatureInfoAdapter.notifyItemChanged(mCaricatureInfoAdapter.getList().size());
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateCurrObject(touchRecyclerView);
            }
        }, 1 * 1000);
    }

    public void clickResetMenu() {
        if (!returnAllStatus()) {
            if (mBottomLinearLayout.getTranslationY() == 0) switchLeftMenu();
            else switchBAndTMenu();
        }
    }

    public void resetTouchRecyclerView() {
        touchRecyclerView.scrollToPosition(0);
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

    /**
     * 启动动画隐藏所有的菜单
     *
     * @return 是否
     */
    public boolean returnAllStatus() {
        boolean isReturn = false;
        if (mLeftLinearLayout.getTranslationX() == 0 && mLeftLinearLayout.getVisibility() == View.VISIBLE) {
            ViewCompat.animate(mLeftLinearLayout).translationX(-mLeftLinearLayout.getWidth()).setDuration(300);
            isReturn = true;
        }
        if (mBottomLinearLayout.getTranslationY() == 0) {
            ViewCompat.animate(mBottomLinearLayout).translationY(mBottomLinearLayout.getHeight()).setDuration(300);
            ViewCompat.animate(toolBarView).translationY(-toolBarView.getHeight()).setDuration(300);
            isReturn = true;
        }
        return isReturn;
    }

    /**
     * 左边菜单切换
     */
    public void switchLeftMenu() {
        switchBAndTMenu();
        if (mLeftLinearLayout.getTranslationX() != 0) {
            ViewCompat.animate(mLeftLinearLayout).translationX(0).setDuration(300);
        } else {
            ViewCompat.animate(mLeftLinearLayout).translationX(-mLeftLinearLayout.getWidth()).setDuration(300);
        }
    }

    public void openLightPop() {
//        switchBAndTMenu();
//        WindowLightPop windowLightPop = new WindowLightPop(this);
//        windowLightPop.showAtLocation(mFrameLayout, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 顶部和底部菜单切换
     */
    public void switchBAndTMenu() {
        if (mBottomLinearLayout.getTranslationY() != 0) {
            ViewCompat.animate(mBottomLinearLayout).translationY(0).setDuration(300);
            ViewCompat.animate(toolBarView).translationY(0).setDuration(300);
        } else {
            ViewCompat.animate(mBottomLinearLayout).translationY(mBottomLinearLayout.getHeight()).setDuration(300);
            ViewCompat.animate(toolBarView).translationY(-toolBarView.getHeight()).setDuration(300);
        }
    }

    /**
     * 更新当前右下角最新集数以及更新seekBar进度
     *
     * @param currPosition 根据当前的位置更新信息
     */
    private void updateCurrIndex(int currPosition) {
        if (mCaricatureChaptersEntity != null) {
            int process = mCaricatureChaptersEntity.getPags().indexOf(mCaricatureInfoAdapter.getObject(currPosition)) + 1;
            if (mCaricatureChaptersEntity != null) {
                int max = mCaricatureChaptersEntity.getPagerSize();
                mSeekBar.setMax(max);
                mSeekBar.setProgress(process);
                String format = String.format(getContext().getString(R.string.current_set_number), mCaricatureChaptersEntity.getTitle(), process, max);
                mTvCurrPager.setText(format);
            }
        }
    }

    /**
     * 更新当前图片所在的对象
     *
     * @param recyclerView re
     * @return 当前位置
     */
    public int updateCurrObject(RecyclerView recyclerView) {
        LinearLayoutManager l = (LinearLayoutManager) recyclerView.getLayoutManager();
        int firstVisibleItemPosition = l.findFirstVisibleItemPosition();
        CaricatureChaptersPageEntity item = mCaricatureInfoAdapter.getObject(firstVisibleItemPosition);
        if (item != null) {
            CaricatureChaptersEntity comicPreViewByIndex = mCaricatureInfoAdapter.getComicPreViewByIndex(item.getIndex());
            if (comicPreViewByIndex != null) mCaricatureChaptersEntity = comicPreViewByIndex;
        }
        return firstVisibleItemPosition;
    }

    /**
     * 更新当前图片所在的对象
     *
     * @return 当前位置
     */
    public int updateCurrObject(int position) {
        CaricatureChaptersPageEntity item = mCaricatureInfoAdapter.getObject(position);
        if (item != null) {
            CaricatureChaptersEntity comicPreViewByIndex = mCaricatureInfoAdapter.getComicPreViewByIndex(item.getIndex());
            if (comicPreViewByIndex != null)
                mCaricatureChaptersEntity = comicPreViewByIndex;
        }
        return position;
    }

    public static boolean isVisBottom(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE) {
            return true;
        } else {
            return false;
        }
    }
}
