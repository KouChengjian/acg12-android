package com.acg12.lib.widget.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.acg12.lib.R;
import com.acg12.lib.entity.Banner;
import com.acg12.lib.listener.BannerClickListener;
import com.acg12.lib.utils.loadimage.ImageLoadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */
public class BannerPagerView extends LinearLayout {
    private Context mContext;
    private CycleViewPager mCycleViewPager;
    private LinearLayout mBannerIndex;
    private BannerClickListener mListener;
    private List<ImageView> indexView = new ArrayList<>();
    private int mInterval = 5000;
    private BannerHandler mHandler = new BannerHandler();
    private BannerPagerAdapter bannerPagerAdapter;

    public BannerPagerView(Context context) {
        this(context, null);
    }

    public BannerPagerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerPagerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.include_banner_pagerview, this);
        mCycleViewPager = (CycleViewPager) view.findViewById(R.id.banner_pager);
        mBannerIndex = (LinearLayout) view.findViewById(R.id.banner_index);
    }

    public void setOnBannerListener(BannerClickListener listener) {
        mListener = listener;
    }

    public void setBanner() {
        List<Banner> urlList = new ArrayList<>();
        urlList.add(new Banner());
        urlList.add(new Banner());
        setBanner(urlList);
    }

    public void setBanner(List<Banner> urlList) {
        if (urlList.isEmpty()) return;
        bannerPagerAdapter = new BannerPagerAdapter(urlList);
        mCycleViewPager.setAdapter(bannerPagerAdapter);
        mCycleViewPager.setCurrentItem(0);
        mCycleViewPager.addOnPageChangeListener(new BannerChangeListener(this));
        // 指示器
        mBannerIndex.removeAllViews();
        indexView.clear();
        for (int i = 0, num = urlList.size(); i < num; i++) {
            ImageView index = new ImageView(mContext);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ;
            layoutParams.setMargins(0, 0, 10, 0);//4个参数按顺序分别是左上右下
            index.setLayoutParams(layoutParams);
            index.setBackgroundResource(R.mipmap.ic_banner_index_normal);

            mBannerIndex.addView(index);
            indexView.add(index);
            setIndexColor(0);
        }
        // 停止轮播
        if (mHandler != null) {
            mHandler.removeMessages(0);
        }
    }

    public void start() {
        // 自动轮播
        mHandler.sendEmptyMessageDelayed(0, mInterval);
    }

    private void setIndexColor(int position) {
        for (int i = 0, num = indexView.size(); i < num; i++) {
            ImageView view = indexView.get(i);
            if (i == position)
                view.setBackgroundResource(R.mipmap.ic_banner_index_press);
            else
                view.setBackgroundResource(R.mipmap.ic_banner_index_normal);
        }
    }

    private class BannerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            scrollToNext();
            sendEmptyMessageDelayed(0, mInterval);
        }
    }

    public void scrollToNext() {
        int index = mCycleViewPager.getCurrentItem() + 1 % indexView.size();
        mCycleViewPager.setCurrentItem(index);
    }

    private class BannerChangeListener implements CycleViewPager.OnPageChangeListener {
        private BannerChangeListener(BannerPagerView bannerPager) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            setIndexColor(position % indexView.size());
            mHandler.removeMessages(0);
            mHandler.sendEmptyMessageDelayed(0, mInterval);
        }
    }

    public List<Banner> getList() {
        return bannerPagerAdapter.getList();
    }

    class BannerPagerAdapter extends PagerAdapter {
        List<Banner> bannerList;

        public BannerPagerAdapter(List<Banner> urlList) {
            this.bannerList = urlList;
        }

        public List<Banner> getList() {
            return bannerList;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return bannerList.size();
        }

        public Object instantiateItem(ViewGroup container, final int position) {
            final Banner banner = bannerList.get(position);
            String url = banner.getImgUrl();
            ImageView item = new ImageView(mContext);
            if (url == null || url.isEmpty()) {
                item.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                item.setScaleType(ImageView.ScaleType.CENTER_CROP);
                item.setBackgroundResource(R.mipmap.bg_loading_pic);
            } else {
                item.setScaleType(ImageView.ScaleType.FIT_XY);
                ImageLoadUtils.glideLoading(mContext, url, item);
            }
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onBannerClick(banner, position);
                    }
                }
            });
            container.addView(item);
            return item;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
