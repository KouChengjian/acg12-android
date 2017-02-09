package org.acg12.db;

import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;

import org.acg12.bean.test;
import org.acg12.config.Constant;

import java.util.ArrayList;


/**
 * Created by DELL on 2016/11/16.
 */
public class DaoBaseImpl implements DaoBase {

    private Context mContext ;
    private DataBase mDataBase;
    private static DaoBaseImpl instance;


    public DaoBaseImpl(Context mContext){
        this.mContext = mContext;
        mDataBase = LiteOrm.newSingleInstance(mContext, Constant.DB_NAME); // 初始化数据库
        instance = this;
    }

    public static DaoBaseImpl getInstance() {
        return instance;
    }

    @Override
    public long saveUser(test user) {
        return mDataBase.save(user);
    }

    @Override
    public test getCurrentUser() {
        ArrayList<test> query = mDataBase.query(test.class);
        if (query == null || query.isEmpty() || query.size() != 1) {
            return null;
        } else {
            return query.get(0);
        }
    }

    @Override
    public long delTabUser(){
        return mDataBase.deleteAll(test.class);
    }


}
