package org.acg12.net;



import org.acg12.bean.Album;
import org.acg12.bean.Palette;
import org.acg12.bean.Video;
import org.acg12.listener.HttpRequestListener;

import java.util.List;

import rx.Subscription;

public interface HttpRequest {

    Subscription albumList(String pinId ,HttpRequestListener<List<Album>> httpRequestListener);

    Subscription paletteList(String pinId ,HttpRequestListener<List<Palette>> httpRequestListener);

    Subscription bangumiList(String page ,HttpRequestListener<List<Video>> httpRequestListener);









//    void updateToken(User user, HttpRequestListener<User> httpRequestListener);
//
//    Subscription login(User user, HttpRequestListener<User> httpRequestListener);
//
//    void register(User user, HttpRequestListener<User> httpRequestListener);
//
//    void verify(User user, HttpRequestListener<User> httpRequestListener);
//
//    void resetPwd(User user, HttpRequestListener<User> httpRequestListener);
//
//    void userinfo(User user, HttpRequestListener<User> httpRequestListener);
}