package org.acg12.net;

import android.content.Context;

import org.acg12.bean.Album;
import org.acg12.bean.Palette;
import org.acg12.bean.User;
import org.acg12.bean.Video;
import org.acg12.db.DaoBaseImpl;
import org.acg12.listener.HttpRequestListener;
import org.acg12.utlis.LogUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by DELL on 2016/11/16.
 */
public class HttpRequestImpl implements HttpRequest {

    private Context mContext ;
    private static HttpRequestImpl instance;

    public HttpRequestImpl(Context mContext){
        this.mContext = mContext;
        instance = this;
    }

    public static HttpRequestImpl getInstance() {
        return instance;
    }

    public Observable<User> isUpdataToken(User user) {
        return Observable.just(user)
                .map(new Func1<User, User>() {
                    @Override
                    public User call(User user) {
                        Boolean hasUpdataToken = false;
                        user.setUpdataToken(hasUpdataToken);
                        return user;
                    }
                });
    }

    @Override
    public Subscription login(final User user, final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = RetrofitClient.with(user).login(user.getUsername(),user.getPassword())
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<User>() {
                    @Override
                    public void call(User u) {
                        user.setUid(u.getUid());
                        user.setNick(u.getNick());
                        user.setSex(u.getSex());
                        user.setAvatar(u.getAvatar());
                        user.setSignature(u.getSignature());
                        DaoBaseImpl.getInstance().delTabUser();
                        DaoBaseImpl.getInstance().saveUser(user);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User u) {
                        httpRequestListener.onSuccess(user);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription register(User user, HttpRequestListener<User> httpRequestListener) {
        return null;
    }

    @Override
    public Subscription verify(User user, HttpRequestListener<User> httpRequestListener) {
        return null;
    }

    @Override
    public Subscription resetPwd(User user, HttpRequestListener<User> httpRequestListener) {
        return null;
    }

    @Override
    public Subscription userInfo(final User user,final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = RetrofitClient.with(user).userInfo()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if (data != null) {
                            JSONObject json = RetrofitClient.getJSONObject(data , "user");
                            user.setUid(RetrofitClient.getInt(json, "id"));
                            user.setSex(RetrofitClient.getInt(json, "sex"));
                            user.setNick(RetrofitClient.getString(json, "nick"));
                            user.setAvatar(RetrofitClient.getString(json, "avatar"));
                            user.setSignature(RetrofitClient.getString(json, "sign"));
                            DaoBaseImpl.getInstance().saveUser(user);
                            httpRequestListener.onSuccess(user);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription avatar(final User user, final HttpRequestListener<User> httpRequestListener) {
        File file = new File(user.getAvatar());
        Map<String, RequestBody> map = new HashMap<String, RequestBody>();
        map.put("alterType", RetrofitClient.parseRequestBody("3"));
        map.put("param1" + "\"; filename=\""+file.getName(), RetrofitClient.parseImageRequestBody(file));
        map.put("param2", RetrofitClient.parseRequestBody(""));

        Subscription subscription = RetrofitClient.with(user).uploadFile(map)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if (data != null) {
                            user.setAvatar(RetrofitClient.getString(data,"avatar"));
                            DaoBaseImpl.getInstance().saveUser(user);
                            httpRequestListener.onSuccess(user);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable, httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription sex(final User user,final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = RetrofitClient.with(user).userAlter("4" , user.getSex()+"" , "")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if (data != null) {
                            DaoBaseImpl.getInstance().saveUser(user);
                            httpRequestListener.onSuccess(user);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription nick(final User user,final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = RetrofitClient.with(user).userAlter("1" , user.getNick() , "")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if (data != null) {
                            DaoBaseImpl.getInstance().saveUser(user);
                            httpRequestListener.onSuccess(user);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription sign(final User user,final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = RetrofitClient.with(user).userAlter("2" , user.getSignature() , "")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if (data != null) {
                            DaoBaseImpl.getInstance().saveUser(user);
                            httpRequestListener.onSuccess(user);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription albumList(String pinId, final HttpRequestListener<List<Album>> httpRequestListener) {
        Subscription subscription = RetrofitClient.with().albumList("album",pinId)
                .subscribeOn(Schedulers.newThread())
//                .unsubscribeOn(Schedulers.io())
//                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        List<Album> list = new ArrayList<Album>();
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            JSONArray array = RetrofitClient.transformJSONObjectToJSONArray(data , "album");
                            for(int i = 0 , num = array.length(); i < num ; i++){
                                JSONObject item = RetrofitClient.getJSONObject(array , i);
                                Album album = new Album();
                                album.setPinId(RetrofitClient.getString(item ,"pinId"));
                                album.setContent(RetrofitClient.getString(item ,"content"));
                                album.setResWidth(RetrofitClient.getInt(item ,"resWidth"));
                                album.setResHight(RetrofitClient.getInt(item ,"resHight"));
                                album.setLove(RetrofitClient.getInt(item ,"love"));
                                album.setFavorites(RetrofitClient.getInt(item ,"favorites"));
                                JSONArray urls = RetrofitClient.getJSONArray(item ,"urlList");
                                if(urls != null){
                                    album.setImageUrl(RetrofitClient.getString(urls , 0));
                                }
                                list.add(album);
                            }
                            httpRequestListener.onSuccess(list);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription paletteList(String pinId, final HttpRequestListener<List<Palette>> httpRequestListener) {
        Subscription subscription = RetrofitClient.with().paletteList("palette",pinId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        List<Palette> list = new ArrayList<Palette>();
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            JSONArray array = RetrofitClient.transformJSONObjectToJSONArray(data , "palette");
                            for(int i = 0 , num = array.length(); i < num ; i++){
                                JSONObject item = RetrofitClient.getJSONObject(array , i);
                                Palette palette = new Palette();
                                palette.setBoardId(RetrofitClient.getString(item ,"boardId"));
                                palette.setName(RetrofitClient.getString(item ,"name"));
                                palette.setNum(RetrofitClient.getInt(item ,"num"));
                                JSONArray urls = RetrofitClient.getJSONArray(item ,"urlAlbum");
                                ArrayList<String> urlAlbum = new ArrayList<String>();
                                for(int j = 0 , n = urls.length() ; j < n ; j ++) {
                                    urlAlbum.add(RetrofitClient.getString(urls , j));
                                }
                                palette.setUrlAlbum(urlAlbum);
                                list.add(palette);
                            }
                            httpRequestListener.onSuccess(list);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription bangumiList(String page,final HttpRequestListener<List<Video>> httpRequestListener) {
        Subscription subscription = RetrofitClient.with().bangumiList(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        List<Video> list = new ArrayList<Video>();
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            JSONArray array = RetrofitClient.transformJSONObjectToJSONArray(data , "video");
                            for(int i = 0 , num = array.length(); i < num ; i++){
                                JSONObject item = RetrofitClient.getJSONObject(array , i);
                                Video video = new Video();
                                video.setTitle(RetrofitClient.getString(item ,"title"));
                                video.setPic(RetrofitClient.getString(item ,"pic"));
                                video.setUpdateContent(RetrofitClient.getString(item ,"updateContent"));
                                //video.setUrlInfo(RetrofitClient.getString(item ,"urlInfo"));
                                //video.setBmId(RetrofitClient.getString(item,"urlInfo").split("/anime/")[1]);
                                video.setBmId(RetrofitClient.getString(item,"bmId"));
                                list.add(video);
                            }
                            httpRequestListener.onSuccess(list);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription videoList(String page , int type ,final HttpRequestListener<List<Video>> httpRequestListener) {
        String requestType = "";
        if(type == 0){
            requestType = "default-24";
        }else if(type == 1){
            requestType = "default-25";
        }else if(type == 2){
            requestType = "default-27";
        }
        Subscription subscription = RetrofitClient.with().videoList("video",requestType,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        List<Video> list = new ArrayList<Video>();
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            JSONArray array = RetrofitClient.transformJSONObjectToJSONArray(data , "video");
                            for(int i = 0 , num = array.length(); i < num ; i++){
                                JSONObject item = RetrofitClient.getJSONObject(array , i);
                                Video video = new Video();
                                video.setAid(RetrofitClient.getString(item ,"aid"));
                                video.setTitle(RetrofitClient.getString(item ,"title"));
                                video.setPlay(RetrofitClient.getString(item ,"play"));
                                video.setVideoReview(RetrofitClient.getString(item ,"videoReview"));
                                video.setFavorites(RetrofitClient.getString(item ,"favorites"));
                                video.setAuthor(RetrofitClient.getString(item ,"author"));
                                video.setDescription(RetrofitClient.getString(item ,"description"));
                                video.setCreate(RetrofitClient.getString(item ,"create"));
                                video.setPic(RetrofitClient.getString(item ,"pic").replace("_320x200.jpg" , ""));
                                list.add(video);
                            }
                            httpRequestListener.onSuccess(list);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription palettePreview(String boardId ,String pinId , final HttpRequestListener<List<Album>> httpRequestListener) {
        Subscription subscription = RetrofitClient.with().palettePreview("palettealbum",pinId ,boardId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        List<Album> list = new ArrayList<Album>();
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            JSONArray array = RetrofitClient.transformJSONObjectToJSONArray(data , "album");
                            for(int i = 0 , num = array.length(); i < num ; i++){
                                JSONObject item = RetrofitClient.getJSONObject(array , i);
                                Album album = new Album();
                                album.setPinId(RetrofitClient.getString(item ,"pinId"));
                                album.setContent(RetrofitClient.getString(item ,"content"));
                                album.setResWidth(RetrofitClient.getInt(item ,"resWidth"));
                                album.setResHight(RetrofitClient.getInt(item ,"resHight"));
                                album.setLove(RetrofitClient.getInt(item ,"love"));
                                album.setFavorites(RetrofitClient.getInt(item ,"favorites"));
                                JSONArray urls = RetrofitClient.getJSONArray(item ,"urlList");
                                if(urls != null){
                                    album.setImageUrl(RetrofitClient.getString(urls , 0));
                                }
                                list.add(album);
                            }
                            httpRequestListener.onSuccess(list);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription bangumiPreview(String bangumiId , final HttpRequestListener<Video> httpRequestListener) {
        Subscription subscription = RetrofitClient.with().bangumiPreview(bangumiId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            Video video = new Video();
                            JSONObject json = RetrofitClient.getJSONObject(data , "video");
                            video.setTitle(RetrofitClient.getString(json,"title"));
                            video.setSbutitle(RetrofitClient.getString(json,"sbutitle"));
                            video.setDescription(RetrofitClient.getString(json,"description"));
                            video.setPic(RetrofitClient.getString(json,"pic").replace("_225x300.jpg",""));

                            List<Video> episodeList = new ArrayList<>();
                            JSONArray allEpisode = RetrofitClient.getJSONArray(json ,"bangumiVideoList");
                            for (int i = 0 , num = allEpisode.length() ; i < num ; i++){
                                Video item = new Video();
                                JSONObject object = RetrofitClient.getJSONObject(allEpisode , i);
                                item.setTitle(RetrofitClient.getString(object,"title"));
                                item.setAid(RetrofitClient.getString(object,"aid"));
                                episodeList.add(item);
                            }
                            video.setEpisodeList(episodeList);

                            List<Video> seasonList = new ArrayList<>();
                            JSONArray allSeason = RetrofitClient.getJSONArray(json ,"quarterVideoList");
                            for (int i = 0 , num = allSeason.length() ; i < num ; i++){
                                Video item = new Video();
                                JSONObject object = RetrofitClient.getJSONObject(allSeason , i);
                                item.setTitle(RetrofitClient.getString(object,"title"));
                                item.setPic(RetrofitClient.getString(object,"pic"));
                                item.setBmId(RetrofitClient.getString(object ,"bmId"));
                                seasonList.add(item);
                            }
                            video.setSeasonList(seasonList);
                            httpRequestListener.onSuccess(video);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription playBangumi(String av,final HttpRequestListener<Video> httpRequestListener) {
        Subscription subscription = RetrofitClient.with().playUrl("bangumi" , av)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            Video video = new Video();
                            JSONObject json = RetrofitClient.getJSONObject(data , "info");
                            video.setPlayUrl(RetrofitClient.getString(json,"url"));
                            video.setCid(RetrofitClient.getString(json,"cid"));
                            video.setPic(RetrofitClient.getString(json,"img"));
                            httpRequestListener.onSuccess(video);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription playVideo(String av, HttpRequestListener<Video> httpRequestListener) {
        return null;
    }

    @Override
    public Subscription searchAlbum(String key , String page,final HttpRequestListener<List<Album>> httpRequestListener) {
        Subscription subscription = RetrofitClient.with().searchAlbum(key,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        List<Album> list = new ArrayList<Album>();
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            JSONArray array = RetrofitClient.transformJSONObjectToJSONArray(data , "album");
                            for(int i = 0 , num = array.length(); i < num ; i++){
                                JSONObject item = RetrofitClient.getJSONObject(array , i);
                                Album album = new Album();
                                album.setPinId(RetrofitClient.getString(item ,"pinId"));
                                album.setContent(RetrofitClient.getString(item ,"content"));
                                album.setResWidth(RetrofitClient.getInt(item ,"resWidth"));
                                album.setResHight(RetrofitClient.getInt(item ,"resHight"));
                                album.setLove(RetrofitClient.getInt(item ,"love"));
                                album.setFavorites(RetrofitClient.getInt(item ,"favorites"));
                                JSONArray urls = RetrofitClient.getJSONArray(item ,"urlList");
                                if(urls != null){
                                    album.setImageUrl(RetrofitClient.getString(urls , 0));
                                }
                                list.add(album);
                            }
                            httpRequestListener.onSuccess(list);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription searchPalette(String key , String page,final HttpRequestListener<List<Palette>> httpRequestListener) {
        Subscription subscription = RetrofitClient.with().searchPalette(key,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        List<Palette> list = new ArrayList<Palette>();
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            JSONArray array = RetrofitClient.transformJSONObjectToJSONArray(data , "palette");
                            for(int i = 0 , num = array.length(); i < num ; i++){
                                JSONObject item = RetrofitClient.getJSONObject(array , i);
                                Palette palette = new Palette();
                                palette.setBoardId(RetrofitClient.getString(item ,"boardId"));
                                palette.setName(RetrofitClient.getString(item ,"name"));
                                palette.setNum(RetrofitClient.getInt(item ,"num"));
                                JSONArray urls = RetrofitClient.getJSONArray(item ,"urlAlbum");
                                ArrayList<String> urlAlbum = new ArrayList<String>();
                                for(int j = 0 , n = urls.length() ; j < n ; j ++) {
                                    urlAlbum.add(RetrofitClient.getString(urls , j));
                                }
                                palette.setUrlAlbum(urlAlbum);
                                list.add(palette);
                            }
                            httpRequestListener.onSuccess(list);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription searchBangumi(String key , String page, final HttpRequestListener<List<Video>> httpRequestListener) {
        Subscription subscription = RetrofitClient.with().searchBangumi(key,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        List<Video> list = new ArrayList<Video>();
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            JSONArray array = RetrofitClient.transformJSONObjectToJSONArray(data , "video");
                            for(int i = 0 , num = array.length(); i < num ; i++){
                                JSONObject item = RetrofitClient.getJSONObject(array , i);
                                Video video = new Video();
                                video.setTitle(RetrofitClient.getString(item ,"title"));
                                video.setPic(RetrofitClient.getString(item ,"pic"));
                                video.setUpdateContent(RetrofitClient.getString(item ,"updateContent"));
                                //video.setUrlInfo(RetrofitClient.getString(item ,"urlInfo"));
                                video.setBmId(RetrofitClient.getString(item,"urlInfo").split("/anime/")[1]);
                                list.add(video);
                            }
                            httpRequestListener.onSuccess(list);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription searchVideo(String key , String page,final HttpRequestListener<List<Video>> httpRequestListener) {
        Subscription subscription = RetrofitClient.with().searchVideo(key,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        List<Video> list = new ArrayList<Video>();
                        JSONObject data = RetrofitClient.parseJSONObject(response);
                        if(data != null){
                            JSONArray array = RetrofitClient.transformJSONObjectToJSONArray(data , "video");
                            for(int i = 0 , num = array.length(); i < num ; i++){
                                JSONObject item = RetrofitClient.getJSONObject(array , i);
                                Video video = new Video();
                                video.setAid(RetrofitClient.getString(item ,"aid"));
                                video.setTitle(RetrofitClient.getString(item ,"title"));
                                video.setPlay(RetrofitClient.getString(item ,"play"));
                                video.setVideoReview(RetrofitClient.getString(item ,"videoReview"));
                                video.setFavorites(RetrofitClient.getString(item ,"favorites"));
                                video.setAuthor(RetrofitClient.getString(item ,"author"));
                                video.setDescription(RetrofitClient.getString(item ,"description"));
                                video.setCreate(RetrofitClient.getString(item ,"create"));
                                video.setPic(RetrofitClient.getString(item ,"pic").replace("_320x200.jpg" , ""));
                                list.add(video);
                            }
                            httpRequestListener.onSuccess(list);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

//    @Override
//    public void updateToken(final User user, HttpRequestListener<User> httpRequestListener) {
//
//    }
//
//    @Override
//    public Subscription login(final User user, final HttpRequestListener<User> httpRequestListener) {
//        Subscription subscription = RetrofitClient.with(user).login(user.getUsername() , user.getPassword())
////                .map(new Func1<User, User>() {
////                    @Override
////                    public User call(User user) {
////                        return user;
////                    }
////                })
//                .flatMap(new Func1<User, Observable<User>>() {
//                    @Override
//                    public Observable<User> call(User u) {
//                        user.setC(u.getC());
//                        user.setUid(u.getUid());
//                        user.setTokenKey(u.getTokenKey());
//                        user.setExpired(u.getExpired());
//                        user.setUpdateTime(u.getUpdateTime());
//                        return RetrofitClient.with(user).userinfo(u.getUid()+"");
//                    }
//                })
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.io())
//                .doOnNext(new Action1<User>() {
//                    @Override
//                    public void call(User user) {
//                        Log.e("doOnNext2","调用");
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<User>() {
//                    @Override
//                    public void call(User user) {
//                        Log.e("User","成功");
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        Log.e("Throwable",throwable.toString()+"");
//                    }
//                });
//        return subscription;
//    }
//
//    @Override
//    public void register(User user, HttpRequestListener<User> httpRequestListener) {
//
//    }
//
//    @Override
//    public void verify(User user, HttpRequestListener<User> httpRequestListener) {
//
//    }
//
//    @Override
//    public void resetPwd(User user, HttpRequestListener<User> httpRequestListener) {
//
//    }
//
//    @Override
//    public void userinfo(final User user, final HttpRequestListener<User> httpRequestListener) {
////        final ApiService apiService = RetrofitClient.with(user);
////        Observable<ResponseBody> observable1 =  apiService.updataToken(user.getTokenKey());
////        Observable<ResponseBody> observable2 =  apiService.userinfo(user.getUid() + "");
////        Observable.merge(observable1, observable2)
////                .subscribeOn(Schedulers.newThread())
////                //.subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(new Subscriber<ResponseBody>() {
////                    @Override
////                    public void onCompleted() {
////                    }
////
////                    @Override
////                    public void onError(Throwable e) {
////                    }
////
////                    @Override
////                    public void onNext(ResponseBody obj) {
////                        try {
////                            Log.e("obj",obj.string()+"===");
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                        }
////                    }
////                });
//    }
}
