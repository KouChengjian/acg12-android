package com.acg12.conf;


import com.acg12.EventBusIndex;
import com.acg12.entity.po.UserEntity;
import com.acg12.entity.event.CommonEnum;
import com.acg12.entity.event.CommonEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.EnumSet;
import java.util.Iterator;

public class EventBusConfig {

    public static final int EventDefault = 1 << 0;
    public static final int EventComm = 1 << 1;
    public static final int EventUser = 1 << 2;
//    public static final int EventPush = 1 << 3;
//    public static final int EventVod = 1 << 4;

    private static EventBus mDefaultEventBus;
    private static EventBus mCommEventBus;
    private static EventBus mUserEventBus;
//    private static EventBus mPushEventBus;
//    private static EventBus mVideoEventBus;

    static {
        mDefaultEventBus = EventBus.builder().addIndex(new EventBusIndex()).installDefaultEventBus();
        mCommEventBus = EventBus.builder().addIndex(new EventBusIndex()).build();
        mUserEventBus = EventBus.builder().addIndex(new EventBusIndex()).build();
//        mPushEventBus = EventBus.builder().addIndex(new EventBusIndex()).build();
//        mVideoEventBus = EventBus.builder().addIndex(new EventBusIndex()).build();
    }

    public static EventBus getBus(int type) {
        switch (type) {
            case EventBusConfig.EventComm:
                return mCommEventBus;
//            case EventUser:
//                return mUserEventBus;
//            case EventPush:
//                return mPushEventBus;
//            case EventVod:
//                return mVideoEventBus;
            default:
            case EventDefault:
                return EventBus.getDefault();
        }
    }

    public static void register(int type, Object subscriber) {
        if ((type & EventDefault) == EventDefault && !mDefaultEventBus.isRegistered(subscriber)) {
            mDefaultEventBus.register(subscriber);
        }
        if ((type & EventComm) == EventComm && !mCommEventBus.isRegistered(subscriber)) {
            mCommEventBus.register(subscriber);
        }
        if ((type & EventUser) == EventUser && !mUserEventBus.isRegistered(subscriber)) {
            mUserEventBus.register(subscriber);
        }
//        if ((type & EventPush) == EventPush && !mPushEventBus.isRegistered(subscriber)) {
//            mPushEventBus.register(subscriber);
//        }
//        if ((type & EventVod) == EventVod && !mVideoEventBus.isRegistered(subscriber)) {
//            mVideoEventBus.register(subscriber);
//        }
    }

    public static void unregister(int type, Object subscriber) {
        if ((type & EventDefault) == EventDefault && mDefaultEventBus.isRegistered(subscriber)) {
            mDefaultEventBus.unregister(subscriber);
        }
        if ((type & EventComm) == EventComm && mCommEventBus.isRegistered(subscriber)) {
            mCommEventBus.unregister(subscriber);
        }
        if ((type & EventUser) == EventUser && mUserEventBus.isRegistered(subscriber)) {
            mUserEventBus.unregister(subscriber);
        }
//        if ((type & EventPush) == EventPush && mPushEventBus.isRegistered(subscriber)) {
//            mPushEventBus.unregister(subscriber);
//        }
//        if ((type & EventVod) == EventVod && mVideoEventBus.isRegistered(subscriber)) {
//            mVideoEventBus.unregister(subscriber);
//        }
    }

    public static void post(int type, Object object) {
        getBus(type).post(object);
    }

    public static void postDefaultEventBus(Object object) {
        mDefaultEventBus.post(object);
    }


    public static void postCommEventBus(CommonEnum type) {
        mCommEventBus.post(new CommonEvent(type));
    }

    public static void postCommEventBus(EnumSet<CommonEnum> types) {
        for (Iterator<CommonEnum> iterator = types.iterator(); iterator.hasNext(); ) {
            mCommEventBus.post(new CommonEvent(iterator.next()));
        }
    }

    public static void postCommEventBus(CommonEnum type, Object object) {
        mCommEventBus.post(new CommonEvent(type, object));
    }

    public static void postCommEventBus(CommonEvent object) {
        mCommEventBus.post(object);
    }

    public static void postUserEventBus(UserEntity object) {
        mUserEventBus.post(object);
    }

//    public static void postPushEventBus(PushEnum type) {
//        mPushEventBus.post(new PushEvent(type));
//    }
//
//    public static void postPushEventBus(PushEnum type, Object object) {
//        mPushEventBus.post(new PushEvent(type, object));
//    }
//
//    public static void postPushEventBus(EnumSet<PushEnum> types) {
//        for (Iterator<PushEnum> iterator = types.iterator(); iterator.hasNext(); ) {
//            mPushEventBus.post(new PushEvent(iterator.next()));
//        }
//    }
//
//    public static void postPushEventBus(PushEvent event) {
//        mPushEventBus.post(event);
//    }
//
//    public static void postVodEventBus(VodEvent event) {
//        mVideoEventBus.post(event);
//    }
//
//    public static void postVodEventBus(VodEvent.EventCode code) {
//        mVideoEventBus.post(new VodEvent(code));
//    }
//
//    public static void postVodEventBus(VodEvent.EventCode code, Object value) {
//        mVideoEventBus.post(new VodEvent(code, value));
//    }
}
