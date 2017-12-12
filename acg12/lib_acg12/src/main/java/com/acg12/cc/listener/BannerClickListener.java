package com.acg12.cc.listener;

/**
 * Created by Administrator on 2017/5/12.
 */


import com.acg12.cc.db.entity.Banner;

/**
 * 横幅点击事件
 */
public interface BannerClickListener {
    void onBannerClick(Banner object, int position);
}