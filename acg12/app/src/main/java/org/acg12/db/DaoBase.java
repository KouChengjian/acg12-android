package org.acg12.db;


import org.acg12.bean.User;
import org.acg12.net.download.DownLoad;

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

}
