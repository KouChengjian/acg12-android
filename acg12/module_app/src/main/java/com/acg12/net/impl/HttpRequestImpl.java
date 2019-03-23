package com.acg12.net.impl;

import android.content.Context;

import com.acg12.cache.DaoBaseImpl;
import com.acg12.conf.AppConfig;
import com.acg12.entity.Album;
import com.acg12.entity.Calendar;
import com.acg12.entity.CaricatureChaptersEntity;
import com.acg12.entity.CaricatureChaptersPageEntity;
import com.acg12.entity.CaricatureEntity;
import com.acg12.entity.CollectCaricatureEntity;
import com.acg12.entity.CollectPaletteEntity;
import com.acg12.entity.CollectSubjectEntity;
import com.acg12.entity.Home;
import com.acg12.entity.News;
import com.acg12.entity.Palette;
import com.acg12.entity.Search;
import com.acg12.entity.Subject;
import com.acg12.entity.SubjectCrt;
import com.acg12.entity.SubjectDetail;
import com.acg12.entity.SubjectOffprint;
import com.acg12.entity.SubjectSong;
import com.acg12.entity.SubjectStaff;
import com.acg12.entity.Update;
import com.acg12.entity.User;
import com.acg12.entity.Video;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.net.RetrofitHttp;
import com.acg12.lib.utils.JsonParse;
import com.acg12.net.HttpRequest;
import com.acg12.net.api.HomeApi;
import com.acg12.net.api.SearchApi;
import com.acg12.net.api.UserApi;
import com.acg12.utlis.URLEncoderUtil;

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
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by DELL on 2016/11/16.
 */
public class HttpRequestImpl implements HttpRequest {

    private static HttpRequestImpl instance;
    private Context mContext;
    private RetrofitHttp mRetrofitHttp;
    private UserApi mUserApi;
    private SearchApi mSearchApi;
    private HomeApi mHomeApi;

    public static HttpRequest init(Context context) {
        return new HttpRequestImpl(context);
    }

    public HttpRequestImpl(Context context) {
        instance = this;
        mContext = context;
        mRetrofitHttp = new RetrofitHttp(mContext, AppConfig.SERVER.baseURL);
        mUserApi = mRetrofitHttp.createApi(UserApi.class);
        mSearchApi = mRetrofitHttp.createApi(SearchApi.class);
        mHomeApi = mRetrofitHttp.createApi(HomeApi.class);
    }

    public static HttpRequestImpl getInstance() {
        return instance;
    }

//    public Observable<User> isUpdataToken() {
//        final User userEntity = LiteOrmImpl.getInstance().getCurrentUser();
//        return Observable.just(userEntity).flatMap(new Func1<UserEntity, Observable<UserEntity>>() {
//            @Override
//            public Observable<UserEntity> call(UserEntity user) {
//                int curTime = (int) (System.currentTimeMillis() / 1000);
//                int tokenContinue = curTime - user.getTokenCreateTime();
//                int tokenTotal = user.getTokenExpireTime() - user.getTokenCreateTime();
////                        LogUtil.e("当前时间 = " + curTime);
////                        LogUtil.e("Token 已经生效 = " + tokenContinue);
////                        LogUtil.e("Token 有效时间 = " + tokenTotal / 3 * 2);
//                if (tokenContinue > tokenTotal / 3 * 2) {
//                    return mUserApi.renewToken();
//                }
//                return Observable.just(user);
//            }
//        }).map(new Func1<UserEntity, UserEntity>() {
//            @Override
//            public UserEntity call(UserEntity user) {
////                        LogUtil.e("userEntity.getToken() = " + userEntity.getToken());
////                        LogUtil.e("user.getToken() = " + user.getToken());
//                if (!userEntity.getToken().equals(user.getToken())) {
//                    PreferencesUtils.putString(mContext, Constant.XML_KEY_TOKEN, user.getToken());
//                }
//                return user;
//            }
//        });
//    }

    public Observable<User> isUpdataToken() {
        final User userEntity = DaoBaseImpl.getInstance(mContext).getCurrentUser();
        return Observable.just(userEntity).map(new Func1<User, User>() {
            @Override
            public User call(User user) {
                Boolean hasUpdataToken = false;
                return user;
            }
        });
    }

    @Override
    public Subscription login(final User user, final HttpRequestListener<User> httpRequestListener) {
        return mUserApi
                .login(user.getUsername(), user.getPassword())
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
                        user.setSessionId(u.getSessionId());
                        user.updataSign();
                        DaoBaseImpl.getInstance(mContext).delTabUser();
                        DaoBaseImpl.getInstance(mContext).saveUser(user);
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<User>() {
                    @Override
                    public void call(User u) {
                        httpRequestListener.onSuccess(user);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitHttp.failure(throwable, httpRequestListener);
                    }
                });
    }

    @Override
    public Subscription register(final User user, final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = mUserApi.register(user.getUsername(), user.getPassword(), user.getVerify()).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.io()).doOnNext(new Action1<User>() {
            @Override
            public void call(User u) {
                user.setUid(u.getUid());
                user.setNick(u.getNick());
                user.setSex(u.getSex());
                user.setAvatar(u.getAvatar());
                user.setSignature(u.getSignature());
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<User>() {
            @Override
            public void call(User response) {
                httpRequestListener.onSuccess(user);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
        return subscription;
    }

    @Override
    public Subscription verify(final User user, final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = mUserApi.verify(user.getUsername(), "1").subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                JSONObject data = RetrofitHttp.parseJSONObject(response);
                if (data != null) {
                    httpRequestListener.onSuccess(user);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
        return subscription;
    }

    @Override
    public Subscription resetPwd(final User user, final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = mUserApi.restPwd(user.getUsername(), user.getPassword(), user.getVerify()).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                JSONObject data = RetrofitHttp.parseJSONObject(response);
                if (data != null) {
                    httpRequestListener.onSuccess(user);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
        return subscription;
    }

    @Override
    public Subscription userInfo(final User user, final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = mUserApi.userInfo().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                JSONObject data = RetrofitHttp.parseJSONObject(response);
                if (data != null) {
                    JSONObject json = JsonParse.getJSONObject(data, "user");
                    user.setUid(JsonParse.getInt(json, "id"));
                    user.setSex(JsonParse.getInt(json, "sex"));
                    user.setNick(JsonParse.getString(json, "nick"));
                    user.setAvatar(JsonParse.getString(json, "avatar"));
                    user.setSignature(JsonParse.getString(json, "sign"));
                    user.updataSign();
                    DaoBaseImpl.getInstance(mContext).saveUser(user);
                    httpRequestListener.onSuccess(user);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
        return subscription;
    }

    @Override
    public Subscription avatar(final User user, final HttpRequestListener<User> httpRequestListener) {
        File file = new File(user.getAvatar());
        Map<String, RequestBody> map = new HashMap<String, RequestBody>();
        map.put("alterType", RetrofitHttp.parseRequestBody("3"));
        map.put("param1" + "\"; filename=\"" + file.getName(), RetrofitHttp.parseImageRequestBody(file));
        map.put("param2", RetrofitHttp.parseRequestBody(""));

        Subscription subscription = mUserApi.uploadAvatar(map).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                JSONObject data = RetrofitHttp.parseJSONObject(response);
                if (data != null) {
                    user.setAvatar(JsonParse.getString(data, "avatar"));
                    DaoBaseImpl.getInstance(mContext).saveUser(user);
                    httpRequestListener.onSuccess(user);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
        return subscription;
    }

    @Override
    public Subscription sex(final User user, final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = mUserApi.userAlter("4", user.getSex() + "", "").subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                JSONObject data = RetrofitHttp.parseJSONObject(response);
                if (data != null) {
                    DaoBaseImpl.getInstance(mContext).saveUser(user);
                    httpRequestListener.onSuccess(user);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
        return subscription;
    }

    @Override
    public Subscription nick(final User user, final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = mUserApi.userAlter("1", user.getNick(), "").subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                JSONObject data = RetrofitHttp.parseJSONObject(response);
                if (data != null) {
                    DaoBaseImpl.getInstance(mContext).saveUser(user);
                    httpRequestListener.onSuccess(user);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
        return subscription;
    }

    @Override
    public Subscription sign(final User user, final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = mUserApi.userAlter("2", user.getSignature(), "").subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                JSONObject data = RetrofitHttp.parseJSONObject(response);
                if (data != null) {
                    DaoBaseImpl.getInstance(mContext).saveUser(user);
                    httpRequestListener.onSuccess(user);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
        return subscription;
    }

    @Override
    public Subscription alterPwd(final User user, final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = mUserApi.userAlter("5", user.getPassword(), user.getNewPassword()).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                JSONObject data = RetrofitHttp.parseJSONObject(response);
                if (data != null) {
                    httpRequestListener.onSuccess(user);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
        return subscription;
    }

    @Override
    public Subscription feedback(final User user, String msg, final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = mUserApi.feedback(msg).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                JSONObject data = RetrofitHttp.parseJSONObject(response);
                if (data != null) {
                    httpRequestListener.onSuccess(user);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
        return subscription;
    }

    @Override
    public Subscription updateApp(User user, final int versionCode, final HttpRequestListener<Update> httpRequestListener) {
        Subscription subscription = mUserApi.updateApp(versionCode + "").subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                JSONObject data = RetrofitHttp.parseJSONObject(response);
                if (data != null) {
                    Update update = new Update();
                    JSONObject json = JsonParse.getJSONObject(data, "update");
                    update.setVersionName(JsonParse.getString(json, "versionName"));
                    update.setVersionCode(JsonParse.getString(json, "versionCode"));
                    update.setMessage(JsonParse.getString(json, "message"));
                    int status = JsonParse.getInt(json, "status");

                    Update up = DaoBaseImpl.getInstance(mContext).getCurrentUpdate();
                    if (up != null) {
                        update.set_Id(up.get_Id());
                        update.setIgnore(up.isIgnore());
                        update.setOldVersionCode(up.getOldVersionCode());
                    }

                    if (status == 2) { // 强制升级
                        update.setDialogStatus(2);
                    } else {
                        if (versionCode >= Integer.valueOf(update.getVersionCode()).intValue()) { // 是否为最新
                            update.setDialogStatus(0);
                        } else if (update.getVersionCode().equals(update.getOldVersionCode())) {  // 是否为忽略版本
                            update.setDialogStatus(3);
                        } else {   // 正常更新
                            update.setDialogStatus(1);
                        }
                    }
                    httpRequestListener.onSuccess(update);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
        return subscription;
    }

    @Override
    public Subscription index(User user, final HttpRequestListener<Home> httpRequestListener) {
        return mHomeApi.index().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                JSONObject data = RetrofitHttp.parseJSONObject(response);
                if (data != null) {
                    Home home = JsonParse.fromJson(data.toString(), Home.class);
                    RetrofitHttp.success(home, httpRequestListener);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
    }

    @Override
    public Subscription newsList(User user, String page, final HttpRequestListener<List<News>> httpRequestListener) {
        return mHomeApi.newsList(page).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                JSONArray data = RetrofitHttp.parseJSONArrayString(response);
                if (data != null) {
                    List<News> list = new ArrayList<>();
                    for (int i = 0, num = data.length(); i < num; i++) {
                        JSONObject item = JsonParse.getJSONObject(data, i);
                        News news = new News();
                        news.setNewsId(JsonParse.getInt(item, "article_id"));
                        news.setCover(JsonParse.getString(item, "pic_url"));
                        news.setTitle(JsonParse.getString(item, "title"));
                        news.setIntro(JsonParse.getString(item, "intro"));
                        news.setLink(JsonParse.getString(item, "page_url"));
                        news.setCreateTime(JsonParse.getInt(item, "create_time"));
                        list.add(news);
                    }
                    httpRequestListener.onSuccess(list);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
    }

    @Override
    public Subscription calendarList(final HttpRequestListener<List<Calendar>> httpRequestListener) {
        return mHomeApi.calendarList().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                JSONArray data = RetrofitHttp.parseJSONArrayString(response);
                if (data != null) {
                    List<Calendar> nameList = new ArrayList<>();
                    for (int i = 0, num = data.length(); i < num; i++) {
                        JSONObject weekdayJson = JsonParse.getJSONObject(data, i);
                        Calendar weekday = new Calendar();
                        JSONObject wekJson = JsonParse.getJSONObject(weekdayJson, "weekday");
                        weekday.setId(JsonParse.getInt(wekJson, "id"));
                        weekday.setCn(JsonParse.getString(wekJson, "cn"));
                        weekday.setEn(JsonParse.getString(wekJson, "en"));
                        weekday.setJa(JsonParse.getString(wekJson, "ja"));
                        List<Calendar> list = new ArrayList<>();
                        weekday.setCalendarList(list);
                        nameList.add(weekday);
                        JSONArray items = JsonParse.getJSONArray(weekdayJson, "items");
                        for (int j = 0, total = items.length(); j < total; j++) {
                            JSONObject itemJson = JsonParse.getJSONObject(items, j);
                            Calendar calendar = new Calendar();
                            calendar.setsId(JsonParse.getInt(itemJson, "id"));
                            calendar.setType(JsonParse.getInt(itemJson, "type"));
                            calendar.setName(JsonParse.getString(itemJson, "name"));
                            calendar.setName_cn(JsonParse.getString(itemJson, "name_cn"));
                            calendar.setSummary(JsonParse.getString(itemJson, "summary"));
                            calendar.setAir_date(JsonParse.getString(itemJson, "air_date"));
                            calendar.setAir_weekday(JsonParse.getInt(itemJson, "air_weekday"));
                            calendar.setImage(JsonParse.getString(itemJson, "image"));
                            list.add(calendar);
                        }
                    }
                    httpRequestListener.onSuccess(nameList);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
    }

    @Override
    public Subscription albumList(User user, String pinId, final HttpRequestListener<List<Album>> httpRequestListener) {
        return mHomeApi.albumList("album", pinId).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                List<Album> list = new ArrayList<>();
                JSONArray data = RetrofitHttp.parseJSONArray(response);
                if (data != null) {
                    for (int i = 0, num = data.length(); i < num; i++) {
                        JSONObject item = JsonParse.getJSONObject(data, i);
                        Album album = new Album();
                        album.setPinId(JsonParse.getString(item, "pinId"));
                        album.setContent(JsonParse.getString(item, "content"));
                        album.setResWidth(JsonParse.getInt(item, "resWidth"));
                        album.setResHight(JsonParse.getInt(item, "resHight"));
                        album.setLove(JsonParse.getInt(item, "love"));
                        album.setFavorites(JsonParse.getInt(item, "favorites"));
                        album.setImageUrl(JsonParse.getString(item, "image"));
                        album.setIsCollect(JsonParse.getInt(item, "isCollect"));
                        list.add(album);
                    }
                    httpRequestListener.onSuccess(list);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
    }

    @Override
    public Subscription subjectInfo(int id, int type, String key, final HttpRequestListener<Subject> httpRequestListener) {
        return mHomeApi.subjectInfo(id, type, key).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                JSONObject data = RetrofitHttp.parseJSONObject(response);
                if (data != null) {
                    Subject subject = new Subject();
                    subject.setSubjectId(JsonParse.getInt(data, "subjectId"));
                    subject.setsId(JsonParse.getInt(data, "sId"));
                    subject.setType(JsonParse.getInt(data, "type"));
                    subject.setTypeName(JsonParse.getString(data, "typeName"));
                    subject.setName(JsonParse.getString(data, "name"));
                    subject.setNameCn(JsonParse.getString(data, "nameCn"));
                    subject.setSummary(JsonParse.getString(data, "summary"));
                    subject.setImage(JsonParse.getString(data, "image"));

                    subject.setEpsCount(JsonParse.getInt(data, "epsCount"));
                    subject.setAirDate(JsonParse.getString(data, "airDate"));
                    subject.setAirWeekday(JsonParse.getInt(data, "airWeekday"));
                    subject.setEndDate(JsonParse.getString(data, "endDate"));
                    subject.setAuthor(JsonParse.getString(data, "author"));

                    subject.setHeight(JsonParse.getString(data, "height"));
                    subject.setWeight(JsonParse.getString(data, "weight"));
                    subject.setAlias(JsonParse.getString(data, "alias"));
                    subject.setGender(JsonParse.getInt(data, "gender"));
                    subject.setBirthday(JsonParse.getString(data, "birthday"));
                    subject.setBloodtype(JsonParse.getInt(data, "bloodtype"));
                    subject.setIsCollect(JsonParse.getInt(data, "isCollect"));

                    List<SubjectDetail> subjectDetailList = new ArrayList<>();
                    JSONArray subjectDetails = JsonParse.getJSONArray(data, "details");
                    for (int i = 0; i < subjectDetails.length(); i++) {
                        JSONObject item = JsonParse.getJSONObject(subjectDetails, i);
                        SubjectDetail subjectDetail = new SubjectDetail();
                        subjectDetail.setOtherTitle(JsonParse.getString(item, "otherTitle"));
                        subjectDetail.setOtherValue(JsonParse.getString(item, "otherValue"));
                        subjectDetailList.add(subjectDetail);
                    }
                    subject.setDetailList(subjectDetailList);

                    List<SubjectStaff> subjectStaffList = new ArrayList<>();
                    JSONArray subjectStaffs = JsonParse.getJSONArray(data, "staff");
                    for (int i = 0; i < subjectStaffs.length(); i++) {
                        JSONObject item = JsonParse.getJSONObject(subjectStaffs, i);
                        SubjectStaff subjectStaff = new SubjectStaff();
                        subjectStaff.setJob(JsonParse.getString(item, "job"));
                        subjectStaff.setName(JsonParse.getString(item, "name"));
                        subjectStaffList.add(subjectStaff);
                    }
                    subject.setStaffList(subjectStaffList);

                    List<SubjectCrt> subjectCrtList = new ArrayList<>();
                    JSONArray subjectCrts = JsonParse.getJSONArray(data, "crt");
                    for (int i = 0; i < subjectCrts.length(); i++) {
                        JSONObject item = JsonParse.getJSONObject(subjectCrts, i);
                        SubjectCrt subjectCrt = new SubjectCrt();
                        subjectCrt.setName(JsonParse.getString(item, "name"));
                        subjectCrt.setNameCn(JsonParse.getString(item, "nameCn"));
                        subjectCrt.setpName(JsonParse.getString(item, "pName"));
                        subjectCrt.setpNameCn(JsonParse.getString(item, "pNameCn"));
                        subjectCrtList.add(subjectCrt);
                    }
                    subject.setCrtList(subjectCrtList);

                    List<SubjectSong> subjectSongList = new ArrayList<>();
                    JSONArray subjectSongs = JsonParse.getJSONArray(data, "song");
                    for (int i = 0; i < subjectSongs.length(); i++) {
                        JSONObject item = JsonParse.getJSONObject(subjectSongs, i);
                        SubjectSong subjectSong = new SubjectSong();
                        subjectSong.setTitle(JsonParse.getString(item, "title"));
                        subjectSongList.add(subjectSong);
                    }
                    subject.setSongList(subjectSongList);

                    List<SubjectOffprint> subjectOffprintList = new ArrayList<>();
                    JSONArray subjectOffprints = JsonParse.getJSONArray(data, "offprint");
                    for (int i = 0; i < subjectOffprints.length(); i++) {
                        JSONObject item = JsonParse.getJSONObject(subjectOffprints, i);
                        SubjectOffprint subjectOffprint = new SubjectOffprint();
                        subjectOffprint.setName(JsonParse.getString(item, "name"));
                        subjectOffprint.setImage(JsonParse.getString(item, "image"));
                        subjectOffprintList.add(subjectOffprint);
                    }
                    subject.setOffprintList(subjectOffprintList);

                    httpRequestListener.onSuccess(subject);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
    }

    @Override
    public Subscription paletteList(User user, String pinId, final HttpRequestListener<List<Palette>> httpRequestListener) {
        Subscription subscription = mHomeApi.paletteList("palette", pinId).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                List<Palette> list = new ArrayList<Palette>();
                JSONObject data = RetrofitHttp.parseJSONObject(response);
                if (data != null) {
                    JSONArray array = JsonParse.getJSONArray(data, "palette");
                    for (int i = 0, num = array.length(); i < num; i++) {
                        JSONObject item = JsonParse.getJSONObject(array, i);
                        Palette palette = new Palette();
                        palette.setBoardId(JsonParse.getString(item, "boardId"));
                        palette.setName(JsonParse.getString(item, "name"));
                        palette.setNum(JsonParse.getInt(item, "num"));
                        JSONArray urls = JsonParse.getJSONArray(item, "urlAlbum");
                        ArrayList<String> urlAlbum = new ArrayList<String>();
                        for (int j = 0, n = urls.length(); j < n; j++) {
                            urlAlbum.add(JsonParse.getString(urls, j));
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
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
        return subscription;
    }

    @Override
    public Subscription bangumiList(User user, String page, final HttpRequestListener<List<Video>> httpRequestListener) {
        Subscription subscription = mHomeApi.bangumiList(page).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                List<Video> list = new ArrayList<Video>();
                JSONObject data = RetrofitHttp.parseJSONObject(response);
                if (data != null) {
                    JSONArray array = JsonParse.getJSONArray(data, "video");
                    for (int i = 0, num = array.length(); i < num; i++) {
                        JSONObject item = JsonParse.getJSONObject(array, i);
                        Video video = new Video();
                        video.setTitle(JsonParse.getString(item, "title"));
                        video.setPic(JsonParse.getString(item, "pic"));
                        video.setUpdateContent(JsonParse.getString(item, "updateContent"));
                        //video.setUrlInfo(RetrofitClient.getString(item ,"urlInfo"));
                        //video.setBmId(RetrofitClient.getString(item,"urlInfo").split("/anime/")[1]);
                        video.setBmId(JsonParse.getString(item, "bmId"));
                        list.add(video);
                    }
                    httpRequestListener.onSuccess(list);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
        return subscription;
    }

    @Override
    public Subscription videoList(User user, String page, int type, final HttpRequestListener<List<Video>> httpRequestListener) {
        String requestType = "";
        if (type == 0) {
            requestType = "default-24";
        } else if (type == 1) {
            requestType = "default-25";
        } else if (type == 2) {
            requestType = "default-27";
        }
        Subscription subscription = mHomeApi.videoList("video", requestType, page).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                List<Video> list = new ArrayList<Video>();
                JSONObject data = RetrofitHttp.parseJSONObject(response);
                if (data != null) {
                    JSONArray array = JsonParse.getJSONArray(data, "video");
                    for (int i = 0, num = array.length(); i < num; i++) {
                        JSONObject item = JsonParse.getJSONObject(array, i);
                        Video video = new Video();
                        video.setAid(JsonParse.getString(item, "aid"));
                        video.setTitle(JsonParse.getString(item, "title"));
                        video.setPlay(JsonParse.getString(item, "play"));
                        video.setVideoReview(JsonParse.getString(item, "videoReview"));
                        video.setFavorites(JsonParse.getString(item, "favorites"));
                        video.setAuthor(JsonParse.getString(item, "author"));
                        video.setDescription(JsonParse.getString(item, "description"));
                        video.setCreate(JsonParse.getString(item, "create"));
                        video.setPic(JsonParse.getString(item, "pic").replace("_320x200.jpg", ""));
                        list.add(video);
                    }
                    httpRequestListener.onSuccess(list);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
        return subscription;
    }

    @Override
    public Subscription palettePreview(User user, String boardId, String pinId, final HttpRequestListener<List<Album>> httpRequestListener) {
        Subscription subscription = mHomeApi.palettePreview("palettealbum", pinId, boardId).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                List<Album> list = new ArrayList<Album>();
                JSONArray data = RetrofitHttp.parseJSONArray(response);
                if (data != null) {
                    for (int i = 0, num = data.length(); i < num; i++) {
                        JSONObject item = JsonParse.getJSONObject(data, i);
                        Album album = new Album();
                        album.setPinId(JsonParse.getString(item, "pinId"));
                        album.setContent(JsonParse.getString(item, "content"));
                        album.setResWidth(JsonParse.getInt(item, "resWidth"));
                        album.setResHight(JsonParse.getInt(item, "resHight"));
                        album.setLove(JsonParse.getInt(item, "love"));
                        album.setFavorites(JsonParse.getInt(item, "favorites"));
                        album.setImageUrl(JsonParse.getString(item, "image"));
                        list.add(album);
                    }
                    httpRequestListener.onSuccess(list);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
        return subscription;
    }

    @Override
    public Subscription bangumiPreview(User user, String bangumiId, final HttpRequestListener<Video> httpRequestListener) {
        Subscription subscription = mHomeApi.bangumiPreview(bangumiId).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                JSONObject data = RetrofitHttp.parseJSONObject(response);
                if (data != null) {
                    Video video = new Video();
                    JSONObject json = JsonParse.getJSONObject(data, "video");
                    video.setTitle(JsonParse.getString(json, "title"));
                    video.setSbutitle(JsonParse.getString(json, "sbutitle"));
                    video.setDescription(JsonParse.getString(json, "description"));
                    video.setPic(JsonParse.getString(json, "pic").replace("_225x300.jpg", ""));

                    List<Video> episodeList = new ArrayList<>();
                    JSONArray allEpisode = JsonParse.getJSONArray(json, "bangumiVideoList");
                    for (int i = 0, num = allEpisode.length(); i < num; i++) {
                        Video item = new Video();
                        JSONObject object = JsonParse.getJSONObject(allEpisode, i);
                        item.setTitle(JsonParse.getString(object, "title"));
                        item.setAid(JsonParse.getString(object, "aid"));
                        episodeList.add(item);
                    }
                    video.setEpisodeList(episodeList);

                    List<Video> seasonList = new ArrayList<>();
                    JSONArray allSeason = JsonParse.getJSONArray(json, "quarterVideoList");
                    for (int i = 0, num = allSeason.length(); i < num; i++) {
                        Video item = new Video();
                        JSONObject object = JsonParse.getJSONObject(allSeason, i);
                        item.setTitle(JsonParse.getString(object, "title"));
                        item.setPic(JsonParse.getString(object, "pic"));
                        item.setBmId(JsonParse.getString(object, "bmId"));
                        seasonList.add(item);
                    }
                    video.setSeasonList(seasonList);
                    httpRequestListener.onSuccess(video);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
        return subscription;
    }

    @Override
    public Subscription playBangumi(User user, String av, final HttpRequestListener<Video> httpRequestListener) {
        Subscription subscription = mHomeApi.playUrl("bangumi", av).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                JSONObject data = RetrofitHttp.parseJSONObject(response);
                if (data != null) {
                    Video video = new Video();
                    JSONObject json = JsonParse.getJSONObject(data, "info");
                    video.setPlayUrl(JsonParse.getString(json, "url"));
                    video.setCid(JsonParse.getString(json, "cid"));
                    video.setPic(JsonParse.getString(json, "img"));
                    httpRequestListener.onSuccess(video);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
        return subscription;
    }

    @Override
    public Subscription playVideo(User user, String av, HttpRequestListener<Video> httpRequestListener) {
        return null;
    }

    @Override
    public Subscription caricatureChapters(final int id, final int type, final HttpRequestListener<CaricatureEntity> httpRequestListener) {
        return isUpdataToken().flatMap(new Func1<User, Observable<ResponseBody>>() {
            @Override
            public Observable<ResponseBody> call(User responseBody) {
                return mHomeApi.caricatureChapters(id, type);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                JSONObject data = RetrofitHttp.parseJSONObjectString(response);
                if (data != null) {
                    CaricatureEntity caricatureEntity = JsonParse.fromJson(data.toString(), CaricatureEntity.class);
                    JSONArray jsonArray = JsonParse.getJSONArray(data, "chaptersList");
                    List<CaricatureChaptersEntity> chaptersList = JsonParse.fromListJson(jsonArray.toString(), CaricatureChaptersEntity.class);
                    caricatureEntity.setChaptersList(chaptersList);
                    RetrofitHttp.success(caricatureEntity, httpRequestListener);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
    }

    @Override
    public Subscription caricatureChaptersPage(final int id, final int index, final int type, final HttpRequestListener<CaricatureChaptersEntity> httpRequestListener) {
        return isUpdataToken().flatMap(new Func1<User, Observable<ResponseBody>>() {
            @Override
            public Observable<ResponseBody> call(User responseBody) {
                return mHomeApi.caricatureChaptersPage(id, index, type);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                JSONObject data = RetrofitHttp.parseJSONObjectString(response);
                if (data != null) {
                    CaricatureChaptersEntity caricatureEntity = JsonParse.fromJson(data.toString(), CaricatureChaptersEntity.class);
                    JSONArray jsonArray = JsonParse.getJSONArray(data, "pags");
                    List<CaricatureChaptersPageEntity> chaptersList = JsonParse.fromListJson(jsonArray.toString(), CaricatureChaptersPageEntity.class);
                    caricatureEntity.setPags(chaptersList);
                    RetrofitHttp.success(caricatureEntity, httpRequestListener);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
    }

    @Override
    public Subscription collectSubjectList(final int pageNumber, final int pageSize, final HttpRequestListener<List<CollectSubjectEntity>> httpRequestListener) {
        return isUpdataToken()
                .flatMap(new Func1<User, Observable<ResponseBody>>() {
                    @Override
                    public Observable<ResponseBody> call(User responseBody) {
                        return mHomeApi.collectSubjectList(pageNumber, pageSize);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONArray data = RetrofitHttp.parseJSONArray(response);
                        if (data != null) {
                            List<CollectSubjectEntity> albums = JsonParse.fromListJson(data.toString(), CollectSubjectEntity.class);
                            RetrofitHttp.success(albums, httpRequestListener);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitHttp.failure(throwable, httpRequestListener);
                    }
                });
    }

    @Override
    public Subscription collectSubjectAdd(final Map<String, Object> params, final HttpRequestListener<String> httpRequestListener) {
        return isUpdataToken()
                .flatMap(new Func1<User, Observable<ResponseBody>>() {
                    @Override
                    public Observable<ResponseBody> call(User responseBody) {
                        return mHomeApi.collectSubjectAdd(params);
                    }
                }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = RetrofitHttp.parseJSONObject(response);
                        if (data != null) {
                            RetrofitHttp.success("", httpRequestListener);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitHttp.failure(throwable, httpRequestListener);
                    }
                });
    }

    @Override
    public Subscription collectSubjectDel(final int relevanceId, final HttpRequestListener<String> httpRequestListener) {
        return isUpdataToken()
                .flatMap(new Func1<User, Observable<ResponseBody>>() {
                    @Override
                    public Observable<ResponseBody> call(User responseBody) {
                        return mHomeApi.collectSubjectDel(relevanceId);
                    }
                }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = RetrofitHttp.parseJSONObject(response);
                        if (data != null) {
                            RetrofitHttp.success("", httpRequestListener);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitHttp.failure(throwable, httpRequestListener);
                    }
                });
    }

    @Override
    public Subscription collectAlbumList(final int pageNumber, final int pageSize, final HttpRequestListener<List<Album>> httpRequestListener) {
        return isUpdataToken()
                .flatMap(new Func1<User, Observable<ResponseBody>>() {
                    @Override
                    public Observable<ResponseBody> call(User responseBody) {
                        return mHomeApi.collectAlbumList(pageNumber, pageSize);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONArray data = RetrofitHttp.parseJSONArray(response);
                        if (data != null) {
                            List<Album> albums = JsonParse.fromListJson(data.toString(), Album.class);
                            RetrofitHttp.success(albums, httpRequestListener);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitHttp.failure(throwable, httpRequestListener);
                    }
                });
    }

    @Override
    public Subscription collectAlbumAdd(final Map<String, Object> params, final HttpRequestListener<String> httpRequestListener) {
        return isUpdataToken()
                .flatMap(new Func1<User, Observable<ResponseBody>>() {
                    @Override
                    public Observable<ResponseBody> call(User responseBody) {
                        return mHomeApi.collectAlbumAdd(params);
                    }
                }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = RetrofitHttp.parseJSONObject(response);
                        if (data != null) {
                            RetrofitHttp.success("", httpRequestListener);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitHttp.failure(throwable, httpRequestListener);
                    }
                });
    }

    @Override
    public Subscription collectAlbumDel(final String pinId, final HttpRequestListener<String> httpRequestListener) {
        return isUpdataToken()
                .flatMap(new Func1<User, Observable<ResponseBody>>() {
                    @Override
                    public Observable<ResponseBody> call(User responseBody) {
                        return mHomeApi.collectAlbumDel(pinId);
                    }
                }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = RetrofitHttp.parseJSONObject(response);
                        if (data != null) {
                            RetrofitHttp.success("", httpRequestListener);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitHttp.failure(throwable, httpRequestListener);
                    }
                });
    }

    @Override
    public Subscription collectPaletteList(final int pageNumber, final int pageSize, final HttpRequestListener<List<CollectPaletteEntity>> httpRequestListener) {
        return isUpdataToken()
                .flatMap(new Func1<User, Observable<ResponseBody>>() {
                    @Override
                    public Observable<ResponseBody> call(User responseBody) {
                        return mHomeApi.collectPaletteList(pageNumber, pageSize);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONArray data = RetrofitHttp.parseJSONArray(response);
                        if (data != null) {
                            List<CollectPaletteEntity> albums = JsonParse.fromListJson(data.toString(), CollectPaletteEntity.class);
                            RetrofitHttp.success(albums, httpRequestListener);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitHttp.failure(throwable, httpRequestListener);
                    }
                });
    }

    @Override
    public Subscription collectPaletteAdd(final Map<String, Object> params, final HttpRequestListener<String> httpRequestListener) {
        return isUpdataToken()
                .flatMap(new Func1<User, Observable<ResponseBody>>() {
                    @Override
                    public Observable<ResponseBody> call(User responseBody) {
                        return mHomeApi.collectPaletteAdd(params);
                    }
                }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = RetrofitHttp.parseJSONObject(response);
                        if (data != null) {
                            RetrofitHttp.success("", httpRequestListener);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitHttp.failure(throwable, httpRequestListener);
                    }
                });
    }

    @Override
    public Subscription collectPaletteDel(final String boardId, final HttpRequestListener<String> httpRequestListener) {
        return isUpdataToken()
                .flatMap(new Func1<User, Observable<ResponseBody>>() {
                    @Override
                    public Observable<ResponseBody> call(User responseBody) {
                        return mHomeApi.collectPaletteDel(boardId);
                    }
                }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = RetrofitHttp.parseJSONObject(response);
                        if (data != null) {
                            RetrofitHttp.success("", httpRequestListener);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitHttp.failure(throwable, httpRequestListener);
                    }
                });
    }

    @Override
    public Subscription collectCaricatureList(final int pageNumber, final int pageSize, final HttpRequestListener<List<CollectCaricatureEntity>> httpRequestListener) {
        return isUpdataToken()
                .flatMap(new Func1<User, Observable<ResponseBody>>() {
                    @Override
                    public Observable<ResponseBody> call(User responseBody) {
                        return mHomeApi.collectCaricatureList(pageNumber, pageSize);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONArray data = RetrofitHttp.parseJSONArray(response);
                        if (data != null) {
                            List<CollectCaricatureEntity> albums = JsonParse.fromListJson(data.toString(), CollectCaricatureEntity.class);
                            RetrofitHttp.success(albums, httpRequestListener);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitHttp.failure(throwable, httpRequestListener);
                    }
                });
    }

    @Override
    public Subscription collectCaricatureAdd(final Map<String, Object> params, final HttpRequestListener<String> httpRequestListener) {
        return isUpdataToken()
                .flatMap(new Func1<User, Observable<ResponseBody>>() {
                    @Override
                    public Observable<ResponseBody> call(User responseBody) {
                        return mHomeApi.collectCaricatureAdd(params);
                    }
                }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = RetrofitHttp.parseJSONObject(response);
                        if (data != null) {
                            RetrofitHttp.success("", httpRequestListener);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitHttp.failure(throwable, httpRequestListener);
                    }
                });
    }

    @Override
    public Subscription collectCaricatureDel(final int comicId, final HttpRequestListener<String> httpRequestListener) {
        return isUpdataToken()
                .flatMap(new Func1<User, Observable<ResponseBody>>() {
                    @Override
                    public Observable<ResponseBody> call(User responseBody) {
                        return mHomeApi.collectCaricatureDel(comicId);
                    }
                }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = RetrofitHttp.parseJSONObject(response);
                        if (data != null) {
                            RetrofitHttp.success("", httpRequestListener);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitHttp.failure(throwable, httpRequestListener);
                    }
                });
    }

    @Override
    public Subscription searchAlbum(User user, String key, String page, final HttpRequestListener<List<Album>> httpRequestListener) {
        return mSearchApi.searchAlbum(key, page).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                List<Album> list = new ArrayList<>();
                JSONArray data = RetrofitHttp.parseJSONArrayString(response);
                if (data != null) {
                    for (int i = 0, num = data.length(); i < num; i++) {
                        JSONObject item = JsonParse.getJSONObject(data, i);
                        Album album = new Album();
                        album.setPinId(JsonParse.getString(item, "pinId"));
                        album.setContent(JsonParse.getString(item, "content"));
                        album.setResWidth(JsonParse.getInt(item, "resWidth"));
                        album.setResHight(JsonParse.getInt(item, "resHight"));
                        album.setLove(JsonParse.getInt(item, "love"));
                        album.setFavorites(JsonParse.getInt(item, "favorites"));
                        album.setImageUrl(JsonParse.getString(item, "image"));
                        list.add(album);
                    }
                    httpRequestListener.onSuccess(list);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
    }

    @Override
    public Subscription searchPalette(User user, String key, String page, final HttpRequestListener<List<Palette>> httpRequestListener) {
        return mSearchApi.searchPalette(key, page).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                List<Palette> list = new ArrayList<Palette>();
                JSONArray data = RetrofitHttp.parseJSONArrayString(response);
                if (data != null) {
                    for (int i = 0, num = data.length(); i < num; i++) {
                        JSONObject item = JsonParse.getJSONObject(data, i);
                        Palette palette = new Palette();
                        palette.setBoardId(JsonParse.getString(item, "boardId"));
                        palette.setName(JsonParse.getString(item, "name"));
                        palette.setNum(JsonParse.getInt(item, "num"));
                        JSONArray urls = JsonParse.getJSONArray(item, "urlAlbum");
                        ArrayList<String> urlAlbum = new ArrayList<String>();
                        for (int j = 0, n = urls.length(); j < n; j++) {
                            urlAlbum.add(JsonParse.getString(urls, j));
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
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
    }

    @Override
    public Subscription searchBangumi(User user, String key, String page, final HttpRequestListener<List<Video>> httpRequestListener) {
        Subscription subscription = mHomeApi.searchBangumi(key, page).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                List<Video> list = new ArrayList<Video>();
                JSONObject data = RetrofitHttp.parseJSONObject(response);
                if (data != null) {
                    JSONArray array = JsonParse.getJSONArray(data, "video");
                    for (int i = 0, num = array.length(); i < num; i++) {
                        JSONObject item = JsonParse.getJSONObject(array, i);
                        Video video = new Video();
                        video.setTitle(JsonParse.getString(item, "title"));
                        video.setPic(JsonParse.getString(item, "pic"));
                        video.setUpdateContent(JsonParse.getString(item, "updateContent"));
                        //video.setUrlInfo(RetrofitClient.getString(item ,"urlInfo"));
                        video.setBmId(JsonParse.getString(item, "urlInfo").split("/anime/")[1]);
                        list.add(video);
                    }
                    httpRequestListener.onSuccess(list);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
        return subscription;
    }

    @Override
    public Subscription searchVideo(User user, String key, String page, final HttpRequestListener<List<Video>> httpRequestListener) {
        Subscription subscription = mHomeApi.searchVideo(key, page).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                List<Video> list = new ArrayList<Video>();
                JSONObject data = RetrofitHttp.parseJSONObject(response);
                if (data != null) {
                    JSONArray array = JsonParse.getJSONArray(data, "video");
                    for (int i = 0, num = array.length(); i < num; i++) {
                        JSONObject item = JsonParse.getJSONObject(array, i);
                        Video video = new Video();
                        video.setAid(JsonParse.getString(item, "aid"));
                        video.setTitle(JsonParse.getString(item, "title"));
                        video.setPlay(JsonParse.getString(item, "play"));
                        video.setVideoReview(JsonParse.getString(item, "videoReview"));
                        video.setFavorites(JsonParse.getString(item, "favorites"));
                        video.setAuthor(JsonParse.getString(item, "author"));
                        video.setDescription(JsonParse.getString(item, "description"));
                        video.setCreate(JsonParse.getString(item, "create"));
                        video.setPic(JsonParse.getString(item, "pic").replace("_320x200.jpg", ""));
                        list.add(video);
                    }
                    httpRequestListener.onSuccess(list);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
        return subscription;
    }

    @Override
    public Subscription searchKeyList(User user, String key, final HttpRequestListener<List<Search>> httpRequestListener) {
        return mSearchApi.searchKeyList(URLEncoderUtil.encode(key)).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                List<Search> list = new ArrayList<>();
                JSONObject data = RetrofitHttp.parseJSONObjectString(response);
                if (data != null) {
                    JSONArray array = JsonParse.getJSONArray(data, "list");
                    for (int i = 0, num = array.length(); i < num; i++) {
                        JSONObject item = JsonParse.getJSONObject(array, i);
                        Search search = new Search();
                        search.setSearchId(JsonParse.getInt(item, "pageid"));
                        search.setTitle(JsonParse.getString(item, "title"));
                        search.setSource(JsonParse.getString(item, "source"));

                        list.add(search);
                    }
                    httpRequestListener.onSuccess(list);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
    }

    @Override
    public Subscription searchSubjectList(User user, final String key, final HttpRequestListener<List<Search>> httpRequestListener) {
        return isUpdataToken()
                .flatMap(new Func1<User, Observable<ResponseBody>>() {
                    @Override
                    public Observable<ResponseBody> call(User responseBody) {
                        return mSearchApi.searchSubjectList(key);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        List<Search> list = new ArrayList<>();
                        JSONObject data = RetrofitHttp.parseJSONObjectString(response);
                        if (data != null) {
                            JSONArray array = JsonParse.getJSONArray(data, "list");
                            for (int i = 0, num = array.length(); i < num; i++) {
                                JSONObject item = JsonParse.getJSONObject(array, i);
                                Search search = new Search();
                                search.setSearchId(JsonParse.getInt(item, "id"));
                                search.setTitle(JsonParse.getString(item, "name"));
                                search.setNameCn(JsonParse.getString(item, "name_cn"));
                                search.setSource(JsonParse.getString(item, "image"));
                                search.setType(JsonParse.getInt(item, "type"));
                                search.setTypeName(JsonParse.getString(item, "typeName"));
                                list.add(search);
                            }
                            RetrofitHttp.success(list, httpRequestListener);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        RetrofitHttp.failure(throwable, httpRequestListener);
                    }
                });
    }

    @Override
    public Subscription searchCaricatureList(final String key, final String page, final HttpRequestListener<List<CaricatureEntity>> httpRequestListener) {
        return isUpdataToken().flatMap(new Func1<User, Observable<ResponseBody>>() {
            @Override
            public Observable<ResponseBody> call(User responseBody) {
                return mSearchApi.searchCaricatureList(key, page);
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<ResponseBody>() {
            @Override
            public void call(ResponseBody response) {
                JSONArray data = RetrofitHttp.parseJSONArray(response);
                if (data != null) {
                    List<CaricatureEntity> list = JsonParse.fromListJson(data.toString(), CaricatureEntity.class);
                    RetrofitHttp.success(list, httpRequestListener);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                RetrofitHttp.failure(throwable, httpRequestListener);
            }
        });
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
////        final AllApi apiService = RetrofitClient.with(user);
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
