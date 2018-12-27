package com.acg12.conf;

import org.greenrobot.eventbus.EventBus;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2018/12/15 18:57
 * Description:
 */
public class EventConfig {

    private static EventConfig mEventBusConfig;
    private static EventBus eventbusUser;
    private static EventBus eventbusNavigation;

    public EventConfig() {
        mEventBusConfig = this;
        eventbusUser = EventBus.builder().build();
        eventbusNavigation = EventBus.builder().build();
    }

    public static EventConfig get() {
        if (mEventBusConfig == null) {
            mEventBusConfig = new EventConfig();
        }
        return mEventBusConfig;
    }

    public EventBus getUserEvent() {
        return eventbusUser;
    }

    public EventBus getNavigationEvent() {
        return eventbusNavigation;
    }
}
