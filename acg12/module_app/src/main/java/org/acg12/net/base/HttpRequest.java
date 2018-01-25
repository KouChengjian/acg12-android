package org.acg12.net.base;


import com.acg12.common.entity.User;
import com.acg12.kk.listener.HttpRequestListener;

import org.acg12.entity.Album;
import org.acg12.entity.Home;
import org.acg12.entity.Palette;
import org.acg12.entity.Search;
import org.acg12.entity.Video;

import java.util.List;

import rx.Subscription;

public interface HttpRequest {

    Subscription index(User user , HttpRequestListener<Home> httpRequestListener);

    Subscription albumList(User user , String pinId , HttpRequestListener<List<Album>> httpRequestListener);

    Subscription paletteList(User user ,String pinId ,HttpRequestListener<List<Palette>> httpRequestListener);

    Subscription bangumiList(User user ,String page ,HttpRequestListener<List<Video>> httpRequestListener);

    Subscription videoList(User user ,String page , int type , HttpRequestListener<List<Video>> httpRequestListener);

    Subscription palettePreview(User user ,String boardId ,String pinId ,HttpRequestListener<List<Album>> httpRequestListener);

    Subscription bangumiPreview(User user ,String av ,HttpRequestListener<Video> httpRequestListener);

    Subscription playBangumi(User user ,String av ,HttpRequestListener<Video> httpRequestListener);

    Subscription playVideo(User user ,String av ,HttpRequestListener<Video> httpRequestListener);

    Subscription searchAlbum(User user ,String key , String page,HttpRequestListener<List<Album>> httpRequestListener);

    Subscription searchPalette(User user ,String key , String page,HttpRequestListener<List<Palette>> httpRequestListener);

    Subscription searchBangumi(User user ,String key , String page ,HttpRequestListener<List<Video>> httpRequestListener);

    Subscription searchVideo(User user ,String key , String page , HttpRequestListener<List<Video>> httpRequestListener);

    Subscription searchKeyList(User user ,String key ,HttpRequestListener<List<Search>> httpRequestListener);

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