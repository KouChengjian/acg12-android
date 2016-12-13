package org.acg12.net;

import android.content.Context;
import android.util.Log;


import org.acg12.bean.User;
import org.acg12.listener.HttpRequestListener;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by DELL on 2016/11/16.
 */
public class HttpRequestImpl implements HttpRequest{

    private Context mContext ;
    private static HttpRequestImpl instance;

    public HttpRequestImpl(Context mContext){
        this.mContext = mContext;
        instance = this;
    }

    public static HttpRequestImpl getInstance() {
        return instance;
    }

    @Override
    public void updateToken(final User user, HttpRequestListener<User> httpRequestListener) {

    }

    @Override
    public Subscription login(final User user, final HttpRequestListener<User> httpRequestListener) {
        Subscription subscription = RetrofitClient.with(user).login(user.getUsername() , user.getPassword())
//                .map(new Func1<User, User>() {
//                    @Override
//                    public User call(User user) {
//                        return user;
//                    }
//                })
                .flatMap(new Func1<User, Observable<User>>() {
                    @Override
                    public Observable<User> call(User u) {
                        user.setC(u.getC());
                        user.setUid(u.getUid());
                        user.setTokenKey(u.getTokenKey());
                        user.setExpired(u.getExpired());
                        user.setUpdateTime(u.getUpdateTime());
                        return RetrofitClient.with(user).userinfo(u.getUid()+"");
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        Log.e("doOnNext2","调用");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        Log.e("User","成功");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("Throwable",throwable.toString()+"");
                    }
                });
        return subscription;
    }

    @Override
    public void register(User user, HttpRequestListener<User> httpRequestListener) {

    }

    @Override
    public void verify(User user, HttpRequestListener<User> httpRequestListener) {

    }

    @Override
    public void resetPwd(User user, HttpRequestListener<User> httpRequestListener) {

    }

    @Override
    public void userinfo(final User user, final HttpRequestListener<User> httpRequestListener) {
//        final ApiService apiService = RetrofitClient.with(user);
//        Observable<ResponseBody> observable1 =  apiService.updataToken(user.getTokenKey());
//        Observable<ResponseBody> observable2 =  apiService.userinfo(user.getUid() + "");
//        Observable.merge(observable1, observable2)
//                .subscribeOn(Schedulers.newThread())
//                //.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<ResponseBody>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                    }
//
//                    @Override
//                    public void onNext(ResponseBody obj) {
//                        try {
//                            Log.e("obj",obj.string()+"===");
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
    }
}
