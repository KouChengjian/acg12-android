package org.acg12.net;



import org.acg12.bean.Album;
import org.acg12.bean.Palette;
import org.acg12.bean.Update;
import org.acg12.bean.User;
import org.acg12.bean.Video;
import org.acg12.listener.HttpRequestListener;

import java.util.List;

import rx.Subscription;

public interface HttpRequest {

    Subscription login(User user, HttpRequestListener<User> httpRequestListener);

    Subscription register(User user, HttpRequestListener<User> httpRequestListener);

    Subscription verify(User user, HttpRequestListener<User> httpRequestListener);

    Subscription resetPwd(User user, HttpRequestListener<User> httpRequestListener);

    Subscription userInfo(User user ,HttpRequestListener<User> httpRequestListener);

    Subscription avatar(User user ,HttpRequestListener<User> httpRequestListener);

    Subscription sex(User user ,HttpRequestListener<User> httpRequestListener);

    Subscription nick(User user ,HttpRequestListener<User> httpRequestListener);

    Subscription sign(User user ,HttpRequestListener<User> httpRequestListener);

    Subscription alterPwd(User user ,HttpRequestListener<User> httpRequestListener);

    Subscription feedback(User user , String msg,HttpRequestListener<User> httpRequestListener);

    Subscription updateApp(User user , int versionCode,HttpRequestListener<Update> httpRequestListener);

    Subscription albumList(String pinId ,HttpRequestListener<List<Album>> httpRequestListener);

    Subscription paletteList(String pinId ,HttpRequestListener<List<Palette>> httpRequestListener);

    Subscription bangumiList(String page ,HttpRequestListener<List<Video>> httpRequestListener);

    Subscription videoList(String page , int type , HttpRequestListener<List<Video>> httpRequestListener);

    Subscription palettePreview(String boardId ,String pinId ,HttpRequestListener<List<Album>> httpRequestListener);

    Subscription bangumiPreview(String av ,HttpRequestListener<Video> httpRequestListener);

    Subscription playBangumi(String av ,HttpRequestListener<Video> httpRequestListener);

    Subscription playVideo(String av ,HttpRequestListener<Video> httpRequestListener);

    Subscription searchAlbum(String key , String page,HttpRequestListener<List<Album>> httpRequestListener);

    Subscription searchPalette(String key , String page,HttpRequestListener<List<Palette>> httpRequestListener);

    Subscription searchBangumi(String key , String page ,HttpRequestListener<List<Video>> httpRequestListener);

    Subscription searchVideo(String key , String page , HttpRequestListener<List<Video>> httpRequestListener);

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