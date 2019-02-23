package com.acg12.net;


import com.acg12.entity.Album;
import com.acg12.entity.Calendar;
import com.acg12.entity.CaricatureChaptersEntity;
import com.acg12.entity.CaricatureEntity;
import com.acg12.entity.Home;
import com.acg12.entity.News;
import com.acg12.entity.Palette;
import com.acg12.entity.Search;
import com.acg12.entity.Subject;
import com.acg12.entity.Update;
import com.acg12.entity.User;
import com.acg12.entity.Video;
import com.acg12.lib.listener.HttpRequestListener;

import java.util.List;
import java.util.Map;

import rx.Subscription;

public interface HttpRequest {

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

    /**
     * ------------------------------------------------首页-----------------------------------------------------------
     */
    Subscription index(User user, HttpRequestListener<Home> httpRequestListener);

    Subscription newsList(User user, String page, HttpRequestListener<List<News>> httpRequestListener);

    Subscription calendarList(HttpRequestListener<List<Calendar>> httpRequestListener);

    Subscription albumList(User user, String pinId, HttpRequestListener<List<Album>> httpRequestListener);

    Subscription subjectInfo(int id, int type, String key, HttpRequestListener<Subject> httpRequestListener);

    Subscription paletteList(User user, String pinId, HttpRequestListener<List<Palette>> httpRequestListener);

    Subscription bangumiList(User user, String page, HttpRequestListener<List<Video>> httpRequestListener);

    Subscription videoList(User user, String page, int type, HttpRequestListener<List<Video>> httpRequestListener);

    Subscription palettePreview(User user, String boardId, String pinId, HttpRequestListener<List<Album>> httpRequestListener);

    Subscription bangumiPreview(User user, String av, HttpRequestListener<Video> httpRequestListener);

    Subscription playBangumi(User user, String av, HttpRequestListener<Video> httpRequestListener);

    Subscription playVideo(User user, String av, HttpRequestListener<Video> httpRequestListener);

    Subscription caricatureChapters(int id, int type, HttpRequestListener<CaricatureEntity> httpRequestListener);

    Subscription caricatureChaptersPage(int id, int index, int type, HttpRequestListener<CaricatureChaptersEntity> httpRequestListener);

    Subscription collectAlbumList(int pageNumber, int pageSize, HttpRequestListener<List<Album>> httpRequestListener);

    Subscription collectAlbumAdd(Map<String, Object> params, HttpRequestListener<String> httpRequestListener);

    Subscription collectAlbumDel(String pinId, HttpRequestListener<String> httpRequestListener);

    /**
     * ------------------------------------------------搜索-----------------------------------------------------------
     */
    Subscription searchAlbum(User user, String key, String page, HttpRequestListener<List<Album>> httpRequestListener);

    Subscription searchPalette(User user, String key, String page, HttpRequestListener<List<Palette>> httpRequestListener);

    Subscription searchBangumi(User user, String key, String page, HttpRequestListener<List<Video>> httpRequestListener);

    Subscription searchVideo(User user, String key, String page, HttpRequestListener<List<Video>> httpRequestListener);

    Subscription searchKeyList(User user, String key, HttpRequestListener<List<Search>> httpRequestListener);

    Subscription searchSubjectList(User user, String key, HttpRequestListener<List<Search>> httpRequestListener);

    Subscription searchCaricatureList(String key, String page, HttpRequestListener<List<CaricatureEntity>> httpRequestListener);
}