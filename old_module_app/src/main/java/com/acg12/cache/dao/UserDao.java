package com.acg12.cache.dao;


import com.acg12.entity.User;
import com.acg12.utlis.DBImpl;

import java.util.ArrayList;

public enum UserDao {

    INSTANCE;

    private User curUserEntity;

    public long save(User userEntity) {
        deleteAll();
        curUserEntity = userEntity;
        return DBImpl.save(userEntity);
    }

    public long delete(User userEntity) {
        curUserEntity = null;
        return DBImpl.delete(userEntity);
    }

    public long deleteAll() {
        curUserEntity = null;
        return DBImpl.deleteAll(User.class);
    }

    public User getCurrentUser() {
        if (curUserEntity != null){
            return curUserEntity;
        }
        ArrayList<User> query = (ArrayList<User>) DBImpl.query(User.class);
        if (query == null || query.isEmpty() || query.size() != 1) {
            if (query != null && query.size() > 1) {
                User curUser = null;
                for (int i = 0, num = query.size(); i < num; i++) {
                    User user = query.get(i);
                    if (i != num - 1) {
                        delete(user);
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
