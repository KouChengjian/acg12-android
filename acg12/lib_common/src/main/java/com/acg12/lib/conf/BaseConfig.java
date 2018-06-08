package com.acg12.lib.conf;


import android.content.Context;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by DELL on 2016/11/25.
 */
public class BaseConfig {

    private Context mContext;
    private static EventBus eventbusUser;
    private static EventBus eventbusNavigation;

    static {
        eventbusUser = EventBus.builder().build();
        eventbusNavigation = EventBus.builder().build();
    }

    public BaseConfig(Context mContext) {
        this.mContext = mContext;

    }

    public static EventBus userEventBus() {
        return eventbusUser;
    }

    public static EventBus navigationEventBus() {
        return eventbusNavigation;
    }

}
