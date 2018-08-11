package org.acg12.dao;


import org.acg12.entity.DownLoad;
import org.acg12.entity.Home;
import org.acg12.entity.Update;
import org.acg12.entity.User;

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

    long saveHome(Home user);

    Home queryHome();
}
