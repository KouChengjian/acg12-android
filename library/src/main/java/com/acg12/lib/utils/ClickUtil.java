package com.acg12.lib.utils;


import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.acg12.lib.ui.IPresenter;
import com.acg12.lib.widget.recycle.CommonRecycleview;
import com.acg12.lib.widget.recycle.IRecycleView;
import com.acg12.lib.widget.recycle.ItemClickSupport;

public class ClickUtil {

    public static void recycleClick(IPresenter li, CommonRecycleview commonRecycleView) {
        if (li instanceof SwipeRefreshLayout.OnRefreshListener)
            commonRecycleView.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) li);
        if (li instanceof IRecycleView.LoadingListener)
            commonRecycleView.setLoadingListener((IRecycleView.LoadingListener) li);
        if (li instanceof CommonRecycleview.IRecycleUpdataListener)
            commonRecycleView.setRecycleUpdataListener((CommonRecycleview.IRecycleUpdataListener) li);
        if (li instanceof ItemClickSupport.OnItemClickListener)
            commonRecycleView.setOnItemClickListener((ItemClickSupport.OnItemClickListener) li);
        if (li instanceof ItemClickSupport.OnItemLongClickListener)
            commonRecycleView.setOnItemLongClickListener((ItemClickSupport.OnItemLongClickListener) li);
    }

    public static void click(IPresenter li, View... views) {
        if(li instanceof View.OnClickListener)
            click((View.OnClickListener) li, views);
    }

    public static void longClick(IPresenter li, View... views) {
        if (li instanceof View.OnLongClickListener)
            longClick((View.OnLongClickListener) li, views);
    }

    public static void itemClick(IPresenter li, AdapterView... views) {
        if (li instanceof AdapterView.OnItemClickListener)
            itemClick((AdapterView.OnItemClickListener) li, views);
    }

    public static void itemLongClick(IPresenter li, AdapterView... views) {
        if (li instanceof AdapterView.OnItemLongClickListener)
            itemLongClick((AdapterView.OnItemLongClickListener) li, views);
    }

    public static void itemSelected(IPresenter li, AdapterView... views) {
        if (li instanceof AdapterView.OnItemSelectedListener)
            itemSelected((AdapterView.OnItemSelectedListener) li, views);
    }

    public static void addOnPageChangeListener(IPresenter li, ViewPager... views) {
        if (li instanceof ViewPager.OnPageChangeListener){

            if (views == null || views.length == 0)
                return;
            for (ViewPager v : views)
                v.addOnPageChangeListener((ViewPager.OnPageChangeListener)li);
        }
    }

    public static void click(View.OnClickListener li, View... views) {
        if (views == null || views.length == 0)
            return;
        for (View v : views){
            if(v instanceof Toolbar){
                ((Toolbar)v).setNavigationOnClickListener(li);
            }else{
                v.setOnClickListener(li);
            }
        }
    }

    public static void longClick(View.OnLongClickListener li, View... views) {
        if (views == null || views.length == 0)
            return;
        for (View v : views)
            v.setOnLongClickListener(li);
    }

    public static void itemClick(AdapterView.OnItemClickListener li, AdapterView... views) {
        if (views == null || views.length == 0)
            return;
        for (AdapterView v : views)
            v.setOnItemClickListener(li);
    }

    public static void itemLongClick(AdapterView.OnItemLongClickListener li, AdapterView... views) {
        if (views == null || views.length == 0)
            return;
        for (AdapterView v : views)
            v.setOnItemLongClickListener(li);
    }

    public static void itemSelected(AdapterView.OnItemSelectedListener li, AdapterView... views) {
        if (views == null || views.length == 0)
            return;
        for (AdapterView v : views)
            v.setOnItemSelectedListener(li);
    }
}
