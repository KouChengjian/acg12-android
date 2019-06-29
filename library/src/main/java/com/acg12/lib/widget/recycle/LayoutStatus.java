package com.acg12.lib.widget.recycle;

/**
 * Created with Android Studio.
 * User: kcj
 * Date: 2018/12/26 15:28
 * Description: 错误布局显示样式
 */
public enum LayoutStatus {

    LAYOUT_STATUS_CONTENT, // 显示内容
    LAYOUT_STATUS_CONTENT_HEADER_SHOW, // 显示内容且显示header
    LAYOUT_STATUS_CONTENT_HEADER_HINT, // 显示内容且不显示header
    LAYOUT_STATUS_EMPTY,
    LAYOUT_STATUS_EMPTY_REFRESH,
    LAYOUT_STATUS_NRT, // 网络
    LAYOUT_STATUS_CUSTOM,
}
