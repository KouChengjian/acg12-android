package com.acg12.cache.dto;

import com.acg12.entity.DBImpl;
import com.acg12.entity.po.UserEntity;

import java.util.ArrayList;

public enum UserDao {
    INSTANCE;

    private UserEntity curUserEntity;

    public long save(UserEntity userEntity) {
        deleteAll();
        curUserEntity = userEntity;
        return DBImpl.save(userEntity);
    }

    public long delete(UserEntity userEntity) {
        curUserEntity = null;
        return DBImpl.delete(userEntity);
    }

    public long deleteAll() {
        curUserEntity = null;
        return DBImpl.deleteAll(UserEntity.class);
    }

    public UserEntity getCurrentUser() {
        if (curUserEntity != null) {
            return curUserEntity;
        }
        ArrayList<UserEntity> query = (ArrayList<UserEntity>) DBImpl.query(UserEntity.class);
        if (query == null || query.isEmpty() || query.size() != 1) {
            if (query != null && query.size() > 1) {
                UserEntity curUser = null;
                for (int i = 0, num = query.size(); i < num; i++) {
                    UserEntity user = query.get(i);
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
