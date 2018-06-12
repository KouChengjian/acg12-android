package org.acg12.listener;


import org.acg12.entity.Banner;

/**
 * 横幅点击事件
 */
public interface BannerClickListener {
    void onBannerClick(Banner object, int position);
}