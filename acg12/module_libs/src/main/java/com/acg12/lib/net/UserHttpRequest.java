package com.acg12.lib.net;

import com.acg12.lib.entity.Update;
import com.acg12.lib.entity.User;
import com.acg12.lib.listener.HttpRequestListener;

import rx.Subscription;

/**
 * Created by Administrator on 2017/12/25.
 */
public interface UserHttpRequest {

    Subscription login(User user, HttpRequestListener<User> httpRequestListener);

    Subscription register(User user, HttpRequestListener<User> httpRequestListener);

    Subscription verify(User user, HttpRequestListener<User> httpRequestListener);

    Subscription resetPwd(User user, HttpRequestListener<User> httpRequestListener);

    Subscription userInfo(User user, HttpRequestListener<User> httpRequestListener);

    Subscription avatar(User user, HttpRequestListener<User> httpRequestListener);

    Subscription sex(User user, HttpRequestListener<User> httpRequestListener);

    Subscription nick(User user, HttpRequestListener<User> httpRequestListener);

    Subscription sign(User user, HttpRequestListener<User> httpRequestListener);

    Subscription alterPwd(User user, HttpRequestListener<User> httpRequestListener);

    Subscription feedback(User user, String msg, HttpRequestListener<User> httpRequestListener);

    Subscription updateApp(User user, int versionCode, HttpRequestListener<Update> httpRequestListener);
}
