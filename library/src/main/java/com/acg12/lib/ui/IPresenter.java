package com.acg12.lib.ui;

/**
 * Author  SLAN
 * <br>
 * 2018/9/6 11:27
 */
public interface IPresenter<T> {
    void take(T view);
    void destroy(T view);
}
