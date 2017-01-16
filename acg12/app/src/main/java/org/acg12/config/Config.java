package org.acg12.config;


import android.content.Context;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by DELL on 2016/11/25.
 */
public class Config {

    Context mContext ;
    static EventBus eventbusNavigation;

    static {
        eventbusNavigation = EventBus.builder().build();
    }

    public Config(Context mContext){
        this.mContext = mContext;
    }

    public static EventBus navigationEventBus(){
        return eventbusNavigation;
    }

}
