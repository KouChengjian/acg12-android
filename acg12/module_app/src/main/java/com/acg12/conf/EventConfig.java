package com.acg12.conf;

import com.acg12.conf.event.CommonEnum;
import com.acg12.conf.event.CommonEvent;

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
    private static EventBus eventbusCommon;

    public EventConfig() {
        mEventBusConfig = this;
        eventbusUser = EventBus.builder().build();
        eventbusNavigation = EventBus.builder().build();
        eventbusCommon = EventBus.builder().build();
    }

    public static EventConfig get() {
        if (mEventBusConfig == null) {
            mEventBusConfig = new EventConfig();
        }
        return mEventBusConfig;
    }

    public EventBus getCommon() {
        return eventbusCommon;
    }

    public void postCommon(CommonEnum commonEnum, Object o) {
        getCommon().post(new CommonEvent(commonEnum, o));
    }

    public EventBus getUserEvent() {
        return eventbusUser;
    }

    public EventBus getNavigationEvent() {
        return eventbusNavigation;
    }
}
