package org.acg12.ui.views;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acg12.lib.ui.adapter.CommonPagerAdapter;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.utils.glide.ImageLoadUtils;
import com.acg12.lib.widget.TipLayoutView;
import com.acg12.lib.widget.ToolBarView;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.acg12.R;
import org.acg12.entity.Subject;
import org.acg12.ui.fragment.SearchAlbumFragment;
import org.acg12.ui.fragment.SearchAnimatFragment;
import org.acg12.ui.fragment.SearchBangunFragment;
import org.acg12.ui.fragment.SearchIntroFragment;
import org.acg12.ui.fragment.SearchPaletteFragment;
import org.acg12.ui.fragment.SearchVideoFragment;
import org.acg12.utlis.BitmapBlurUtil;

import butterknife.BindView;

/**
 * Created by DELL on 2017/1/3.
 */
public class SearchInfoView extends ViewImpl {

    @BindView(R.id.toolBarView)
    ToolBarView mToolBarView;
    @BindView(R.id.tipLayoutView)
    protected TipLayoutView mTipLayoutView;
    @BindView(R.id.coordinatorLayout)
    protected CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.search_appbar)
    protected AppBarLayout mAppBarLayout;
    @BindView(R.id.collapsingToolbarLayout)
    protected CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.layout_search_header)
    protected RelativeLayout mLayoutSearchHeader;
    @BindView(R.id.iv_header_bg)
    ImageView iv_header_bg;
    @BindView(R.id.iv_header_pic)
    ImageView iv_header_pic;
    @BindView(R.id.tv_header_title)
    TextView tv_header_title;
    @BindView(R.id.tv_header_title_type)
    TextView tv_header_title_type;
    @BindView(R.id.tv_header_subtitle)
    TextView tv_header_subtitle;
    @BindView(R.id.tv_header_play_eps)
    TextView tv_header_play_eps;
    @BindView(R.id.tv_header_play_time)
    TextView tv_header_play_time;
    @BindView(R.id.search_toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.search_tabLayout)
    protected TabLayout mTabLayout;
    @BindView(R.id.search_viewpager)
    protected ViewPager mViewpager;


    private Fragment[] fragments;
    private String[] tabTitles;
    private CommonPagerAdapter commonPagerAdapter;
    private SearchIntroFragment searchIntroFragment;
    private SearchAlbumFragment searchAlbumFragment;
    private SearchPaletteFragment searchPaletteFragment;
    private SearchVideoFragment searchVideoFragment;
    private SearchAnimatFragment searchAnimatFragment;

    Bitmap mBlurBitmap;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_info;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);

        mCollapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        mCollapsingToolbarLayout.setTitleEnabled(false);

        tabTitles = new String[]{"简介", "插画", "画册", "动画", "漫画"};
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter, toolbar, mToolBarView.getToolbar());
        mTipLayoutView.setOnReloadClick((TipLayoutView.OnReloadClick) mPresenter);
    }

    public void setTitle(String title) {
        mToolBarView.setNavigationOrBreak(title);
    }

    public void bindData(int id, int type, String title, Subject subject) {
        toolbar.setTitle(subject.getName());
        tv_header_title.setText(subject.getNameCn().isEmpty() ? subject.getName() : subject.getNameCn());
        String url = subject.getImage();
        if (!url.contains("http")){
            url = "http:"+url;
        }
        ImageLoadUtils.glideLoading(url, iv_header_pic);
        ImageLoadUtils.glideLoading(url, new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                mBlurBitmap = BitmapBlurUtil.rsBlur(getContext(), resource, 24);
                iv_header_bg.setImageBitmap(mBlurBitmap);
            }
        });

        if(type == 0){
            tv_header_title_type.setText(String.format("（%s）", subject.getTypeStatus()));
            String author = (subject.getAuthor() == null || subject.getAuthor().isEmpty() || subject.getAuthor().equals("null")) ? "???" : subject.getAuthor();
            tv_header_subtitle.setText("作者 "+ author);
            tv_header_play_eps.setText(subject.getTypeEps());
            tv_header_play_time.setText(subject.getTypeTime());
        } else {
            tv_header_title_type.setText("");
            tv_header_subtitle.setVisibility(View.GONE);
            tv_header_play_eps.setText(subject.getAlias());
            tv_header_play_time.setText(subject.getOther());
        }

        searchIntroFragment = SearchIntroFragment.newInstance(title, subject);
        searchAlbumFragment = SearchAlbumFragment.newInstance(subject.getNameCn().isEmpty() ? subject.getName() : subject.getNameCn());
        searchPaletteFragment = SearchPaletteFragment.newInstance(subject.getNameCn().isEmpty() ? subject.getName() : subject.getNameCn());
        searchVideoFragment = SearchVideoFragment.newInstance(subject.getNameCn().isEmpty() ? subject.getName() : subject.getNameCn());
        searchAnimatFragment = SearchAnimatFragment.newInstance(subject.getNameCn().isEmpty() ? subject.getName() : subject.getNameCn());
        fragments = new Fragment[]{searchIntroFragment, searchAlbumFragment, searchPaletteFragment, searchVideoFragment, searchAnimatFragment};

        commonPagerAdapter = new CommonPagerAdapter(((AppCompatActivity) getContext()).getSupportFragmentManager(), fragments, tabTitles);
        mViewpager.setAdapter(commonPagerAdapter);
        mViewpager.setOffscreenPageLimit(fragments.length);
        mTabLayout.setupWithViewPager(mViewpager);
    }

    public TabLayout getTabLayout() {
        return mTabLayout;
    }

    public TipLayoutView getTipLayoutView() {
        return mTipLayoutView;
    }

    public void startProgress() {
        mTipLayoutView.showLoading();
        mToolBarView.setVisibility(View.VISIBLE);
        mAppBarLayout.setVisibility(View.GONE);
        mViewpager.setVisibility(View.GONE);
    }

    public void stopProgress() {
        mTipLayoutView.showLoading();
        mTipLayoutView.setVisibility(View.GONE);
        mToolBarView.setVisibility(View.GONE);
        mAppBarLayout.setVisibility(View.VISIBLE);
        mViewpager.setVisibility(View.VISIBLE);
    }

    public void stopProgressOrError() {
        mTipLayoutView.showNetError();
        mToolBarView.setVisibility(View.VISIBLE);
        mAppBarLayout.setVisibility(View.GONE);
        mViewpager.setVisibility(View.GONE);
    }


    public void onDestroy() {
        if (mBlurBitmap != null) {
            mBlurBitmap.recycle();
            mBlurBitmap = null;
        }
    }
}
