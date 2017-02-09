package org.acg12.db;


import org.acg12.bean.test;

/**
 * Created by DELL on 2016/11/16.
 */
public interface DaoBase {

    long saveUser(test user);

    long delTabUser();

    test getCurrentUser();

}
