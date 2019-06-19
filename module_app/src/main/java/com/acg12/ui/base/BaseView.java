package com.acg12.ui.base;

import android.arch.lifecycle.Lifecycle;

import com.acg12.lib.ui.IView;
import com.trello.rxlifecycle2.LifecycleProvider;


/**
 * Author  SLAN
 * <br>
 * 2018/9/5 20:18
 */
public interface BaseView extends IView {
    LifecycleProvider<Lifecycle.Event> getLifeCycleProvider();
}
