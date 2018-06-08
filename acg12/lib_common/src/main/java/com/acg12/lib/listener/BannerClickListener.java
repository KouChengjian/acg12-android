package com.acg12.lib.listener;

import com.acg12.lib.entity.Banner;

/**
 * 横幅点击事件
 */
public interface BannerClickListener {
    void onBannerClick(Banner object, int position);
}