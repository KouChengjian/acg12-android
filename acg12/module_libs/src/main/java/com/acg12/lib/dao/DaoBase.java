package com.acg12.lib.dao;


import com.acg12.lib.entity.DownLoad;
import com.acg12.lib.entity.Update;
import com.acg12.lib.entity.User;

import java.util.List;

/**
 * Created by DELL on 2016/11/16.
 */
public interface DaoBase {

    long saveUser(User user);

    long delTabUser();

    User getCurrentUser();

    long saveDownload(DownLoad download);

    List<DownLoad> queryDownloadList();

    DownLoad queryUrlDownLoad(String url);

    long delDownLoad(String url);

    long delDownLoad(DownLoad Download);

    long saveUpdate(Update update);

    Update getCurrentUpdate();

    long delTabUpdate();
}
