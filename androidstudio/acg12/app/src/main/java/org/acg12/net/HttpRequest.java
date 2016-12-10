package org.acg12.net;



import org.acg12.bean.User;
import org.acg12.listener.HttpRequestListener;

import rx.Subscription;

public interface HttpRequest {

    void updateToken(User user, HttpRequestListener<User> httpRequestListener);

    Subscription login(User user, HttpRequestListener<User> httpRequestListener);

    void register(User user, HttpRequestListener<User> httpRequestListener);

    void verify(User user, HttpRequestListener<User> httpRequestListener);

    void resetPwd(User user, HttpRequestListener<User> httpRequestListener);

    void userinfo(User user, HttpRequestListener<User> httpRequestListener);
}