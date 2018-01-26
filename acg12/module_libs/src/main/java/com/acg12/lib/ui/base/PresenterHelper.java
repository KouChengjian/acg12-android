package com.acg12.lib.ui.base;


import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.acg12.lib.ui.IPresenter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class PresenterHelper {


    public static <T> Class<T> getViewClass(Class<?> klass) {
        // getSuperclass()获得该类的父类 class com.test.Person
        // getGenericSuperclass()获得带有泛型的父类  com.test.Person<com.test.Student>
        // Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
        Type type = klass.getGenericSuperclass();
//        LogUtil.e(klass.getSuperclass()+"===");
//        LogUtil.e(klass.getGenericSuperclass()+"===");
        // 是否支持泛型
        if (type == null || !(type instanceof ParameterizedType))
            return null;
        // ParameterizedType参数化类型，即泛型
        ParameterizedType parameterizedType = (ParameterizedType) type;
        // getActualTypeArguments获取参数化类型的数组，泛型可能有多个
        Type[] types = parameterizedType.getActualTypeArguments();
        if (types == null || types.length == 0)
            return null;
        for (int i = 0; i < types.length; i++) {
            Class<T> s = (Class<T>) types[i];
//            LogUtil.e(s.getName());
        }
        return (Class<T>) types[0];
    }


    public static void click(IPresenter li, View... views) {
        if (li instanceof View.OnClickListener)
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
        if (li instanceof ViewPager.OnPageChangeListener) {

            if (views == null || views.length == 0)
                return;
            for (ViewPager v : views)
                v.addOnPageChangeListener((ViewPager.OnPageChangeListener) li);
        }
    }

    public static void click(View.OnClickListener li, View... views) {
        if (views == null || views.length == 0)
            return;
        for (View v : views) {
            if (v instanceof Toolbar) {
                ((Toolbar) v).setNavigationOnClickListener(li);
            } else {
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

    public static void itemClick(AdapterView.OnItemClickListener li,
                                 AdapterView... views) {
        if (views == null || views.length == 0)
            return;
        for (AdapterView v : views)
            v.setOnItemClickListener(li);
    }

    public static void itemLongClick(AdapterView.OnItemLongClickListener li,
                                     AdapterView... views) {
        if (views == null || views.length == 0)
            return;
        for (AdapterView v : views)
            v.setOnItemLongClickListener(li);
    }

    public static void itemSelected(AdapterView.OnItemSelectedListener li,
                                    AdapterView... views) {
        if (views == null || views.length == 0)
            return;
        for (AdapterView v : views)
            v.setOnItemSelectedListener(li);
    }


}
