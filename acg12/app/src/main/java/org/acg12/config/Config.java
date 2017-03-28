package org.acg12.config;


import android.content.Context;

import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by DELL on 2016/11/25.
 */
public class Config {

    Context mContext ;
    static ListVideoUtil listVideoUtil;
    static EventBus eventbusNavigation;

    static {
        eventbusNavigation = EventBus.builder().build();
    }

    public Config(Context mContext){
        this.mContext = mContext;

    }

    // init in activity
    public static void initListVideoUtil(Context mContext){
        listVideoUtil = new ListVideoUtil(mContext);
        listVideoUtil.setHideActionBar(false);
        listVideoUtil.setHideStatusBar(false);
    }

    public static EventBus navigationEventBus(){
        return eventbusNavigation;
    }

    public static ListVideoUtil ListVideoUtilInstance(){
        return listVideoUtil;
    }

}
