package com.acg12.cache;

import android.content.Context;

import com.acg12.constant.Constant;
import com.acg12.entity.DownLoad;
import com.acg12.entity.Home;
import com.acg12.entity.Update;
import com.acg12.entity.User;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by DELL on 2016/11/16.
 */
public class DaoBaseImpl implements DaoBase {

    private DataBase mDataBase;
    private static DaoBaseImpl instance;


    public DaoBaseImpl(Context mContext){
        mDataBase = LiteOrm.newSingleInstance(mContext, Constant.DB_NAME); // 初始化数据库
        instance = this;
    }

    public static DaoBaseImpl getInstance(Context mContext) {
        if (instance == null){
            instance = new DaoBaseImpl(mContext);
        }
        return instance;
    }

    @Override
    public long saveUser(User user) {
        return mDataBase.save(user);
    }

    @Override
    public User getCurrentUser() {
        ArrayList<User> query = mDataBase.query(User.class);
        if (query == null || query.isEmpty() || query.size() != 1) {
            if (query != null && query.size() > 1) {
                User curUser = null;
                for (int i = 0, num = query.size(); i < num; i++) {
                    User user = query.get(i);
                    if (i != num - 1) {
                        mDataBase.delete(user);
                    } else {
                        curUser = user;
                    }
                }
                return curUser;
            } else {
                return null;
            }
        } else {
            return query.get(0);
        }
    }

    @Override
    public long delTabUser(){
        return mDataBase.deleteAll(User.class);
    }

    @Override
    public long saveDownload(DownLoad download) {
        return mDataBase.save(download);
    }

    @Override
    public List<DownLoad> queryDownloadList() {
        return mDataBase.query(DownLoad.class);
    }

    @Override
    public DownLoad queryUrlDownLoad(String url) {
        List<DownLoad> list = queryDownloadList();
        for(DownLoad dl:list){
            if(dl.getName().equals(url)){
                return dl;
            }
        }
        return null;
    }

    @Override
    public long delDownLoad(String url) {
        List<DownLoad> list = queryDownloadList();
        for(DownLoad dl:list){
            if(dl.getUrl().equals(url)){
                return delDownLoad(dl);
            }
        }
        return 0;
    }

    @Override
    public long delDownLoad(DownLoad download) {
        return mDataBase.delete(download);
    }

    @Override
    public long saveUpdate(Update update) {
        return mDataBase.save(update);
    }

    @Override
    public Update getCurrentUpdate() {
        ArrayList<Update> query = mDataBase.query(Update.class);
        if (query == null || query.isEmpty() || query.size() != 1) {
            return null;
        } else {
            return query.get(0);
        }
    }

    @Override
    public long delTabUpdate() {
        return mDataBase.deleteAll(Update.class);
    }

    @Override
    public long saveHome(Home home) {
        return mDataBase.save(home);
    }

    @Override
    public Home queryHome() {
        ArrayList<Home> query = mDataBase.query(Home.class);
        if (query == null || query.isEmpty() || query.size() != 1) {
            if (query != null && query.size() > 1) {
                Home curUser = null;
                for (int i = 0, num = query.size(); i < num; i++) {
                    Home user = query.get(i);
                    if (i != num - 1) {
                        mDataBase.delete(user);
                    } else {
                        curUser = user;
                    }
                }
                return curUser;
            } else {
                return null;
            }
        } else {
            return query.get(0);
        }
    }


}
