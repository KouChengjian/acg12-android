package org.acg12.conf;


import android.content.Context;

import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by DELL on 2016/11/25.
 */
public class Config  {

    private Context mContext;
    private static EventBus eventbusUser;
    private static EventBus eventbusNavigation;
    static ListVideoUtil listVideoUtil;


    public Config(Context mContext){
        this.mContext = mContext;
        eventbusUser = EventBus.builder().build();
        eventbusNavigation = EventBus.builder().build();
    }

    public static EventBus userEventBus() {
        return eventbusUser;
    }

    public static EventBus navigationEventBus() {
        return eventbusNavigation;
    }

    // init in activity
    public static void initListVideoUtil(Context mContext){
        listVideoUtil = new ListVideoUtil(mContext);
        listVideoUtil.setHideActionBar(false);
        listVideoUtil.setHideStatusBar(false);
    }

    public static ListVideoUtil ListVideoUtilInstance(){
        return listVideoUtil;
    }

}
