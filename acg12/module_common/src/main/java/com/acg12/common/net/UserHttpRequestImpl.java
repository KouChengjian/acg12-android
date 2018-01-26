package com.acg12.common.net;

import android.content.Context;

import com.acg12.common.dao.DaoBaseImpl;
import com.acg12.common.entity.Update;
import com.acg12.common.entity.User;
import com.acg12.common.net.base.UserHttpRequest;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.utils.LogUtil;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
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
 * Created by Administrator on 2017/12/25.
 */
public class UserHttpRequestImpl implements UserHttpRequest {

    private Context mContext;
    private static UserHttpRequestImpl instance;

    public UserHttpRequestImpl(Context mContext) {
        this.mContext = mContext;
    }

    public static UserHttpRequestImpl getInstance(Context mContext) {
        if(instance == null){
            instance = new UserHttpRequestImpl(mContext);
        }
        return instance;
    }

    public Observable<User> isUpdataToken(User user) {
        return Observable.just(user)
                .map(new Func1<User, User>() {
                    @Override
                    public User call(User user) {
                        Boolean hasUpdataToken = false;
                        return user;
                    }
                });
    }

    @Override
    public Subscription login(final User user, final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = UserRetrofitClient.with(user).login(user.getUsername(),user.getPassword())
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
                        user.updataSign();
                        DaoBaseImpl.getInstance(mContext).delTabUser();
                        DaoBaseImpl.getInstance(mContext).saveUser(user);
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
                        UserRetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription register(final User user,final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = UserRetrofitClient.with(user).register(user.getUsername() , user.getPassword() , user.getVerify())
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
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User response) {
                        httpRequestListener.onSuccess(user);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        UserRetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription verify(final User user,final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = UserRetrofitClient.with(user).verify(user.getUsername() , "1")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = UserRetrofitClient.parseJSONObject(response);
                        if (data != null) {
                            httpRequestListener.onSuccess(user);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        UserRetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription resetPwd(final User user,final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = UserRetrofitClient.with(user).restPwd(user.getUsername() , user.getPassword() , user.getVerify())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = UserRetrofitClient.parseJSONObject(response);
                        if (data != null) {
                            httpRequestListener.onSuccess(user);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        UserRetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription userInfo(final User user,final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = UserRetrofitClient.with(user).userInfo()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = UserRetrofitClient.parseJSONObject(response);
                        if (data != null) {
                            JSONObject json = UserRetrofitClient.getJSONObject(data , "user");
                            user.setUid(UserRetrofitClient.getInt(json, "id"));
                            user.setSex(UserRetrofitClient.getInt(json, "sex"));
                            user.setNick(UserRetrofitClient.getString(json, "nick"));
                            user.setAvatar(UserRetrofitClient.getString(json, "avatar"));
                            user.setSignature(UserRetrofitClient.getString(json, "sign"));
                            user.updataSign();
                            DaoBaseImpl.getInstance(mContext).saveUser(user);
                            httpRequestListener.onSuccess(user);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        UserRetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription avatar(final User user, final HttpRequestListener<User> httpRequestListener) {
        File file = new File(user.getAvatar());
        Map<String, RequestBody> map = new HashMap<String, RequestBody>();
        map.put("alterType", UserRetrofitClient.parseRequestBody("3"));
        map.put("param1" + "\"; filename=\""+file.getName(), UserRetrofitClient.parseImageRequestBody(file));
        map.put("param2", UserRetrofitClient.parseRequestBody(""));

        Subscription subscription = UserRetrofitClient.with(user).uploadAvatar(map)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = UserRetrofitClient.parseJSONObject(response);
                        if (data != null) {
                            user.setAvatar(UserRetrofitClient.getString(data,"avatar"));
                            DaoBaseImpl.getInstance(mContext).saveUser(user);
                            httpRequestListener.onSuccess(user);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        UserRetrofitClient.failure(throwable, httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription sex(final User user,final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = UserRetrofitClient.with(user).userAlter("4" , user.getSex()+"" , "")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = UserRetrofitClient.parseJSONObject(response);
                        if (data != null) {
                            DaoBaseImpl.getInstance(mContext).saveUser(user);
                            httpRequestListener.onSuccess(user);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        UserRetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription nick(final User user,final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = UserRetrofitClient.with(user).userAlter("1" , user.getNick() , "")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = UserRetrofitClient.parseJSONObject(response);
                        if (data != null) {
                            DaoBaseImpl.getInstance(mContext).saveUser(user);
                            httpRequestListener.onSuccess(user);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        UserRetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription sign(final User user,final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = UserRetrofitClient.with(user).userAlter("2" , user.getSignature() , "")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = UserRetrofitClient.parseJSONObject(response);
                        if (data != null) {
                            DaoBaseImpl.getInstance(mContext).saveUser(user);
                            httpRequestListener.onSuccess(user);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        UserRetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription alterPwd(final User user,final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = UserRetrofitClient.with(user).userAlter("5" , user.getPassword() , user.getNewPassword())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = UserRetrofitClient.parseJSONObject(response);
                        if (data != null) {
                            httpRequestListener.onSuccess(user);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        UserRetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription feedback(final User user, String msg,final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = UserRetrofitClient.with(user).feedback(msg)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = UserRetrofitClient.parseJSONObject(response);
                        if (data != null) {
                            httpRequestListener.onSuccess(user);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        UserRetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }

    @Override
    public Subscription updateApp(User user, final int versionCode, final HttpRequestListener<Update> httpRequestListener) {
        Subscription subscription = UserRetrofitClient.with(user).updateApp(versionCode+"")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody response) {
                        JSONObject data = UserRetrofitClient.parseJSONObject(response);
                        if (data != null) {
                            Update update = new Update();
                            JSONObject json = UserRetrofitClient.getJSONObject(data , "update");
                            update.setVersionName(UserRetrofitClient.getString(json , "versionName"));
                            update.setVersionCode(UserRetrofitClient.getString(json , "versionCode"));
                            update.setMessage(UserRetrofitClient.getString(json , "message"));
                            int status = UserRetrofitClient.getInt(json , "status");

                            Update up = DaoBaseImpl.getInstance(mContext).getCurrentUpdate();
                            if(up != null){
                                update.setId(up.getId() );
                                update.setIgnore(up.isIgnore());
                                update.setOldVersionCode(up.getOldVersionCode());
                            }

                            if(status == 2){ // 强制升级
                                update.setDialogStatus(2);
                            } else {
                                if(versionCode >= Integer.valueOf(update.getVersionCode()).intValue()){ // 是否为最新
                                    update.setDialogStatus(0);
                                } else if(update.getVersionCode().equals(update.getOldVersionCode())){  // 是否为忽略版本
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
                        UserRetrofitClient.failure(throwable , httpRequestListener);
                    }
                });
        return subscription;
    }
}
