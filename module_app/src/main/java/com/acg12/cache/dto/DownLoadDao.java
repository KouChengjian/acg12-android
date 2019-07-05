package com.acg12.cache.dto;

import com.acg12.entity.DBImpl;
import com.acg12.entity.po.DownLoadEntity;
import com.acg12.entity.po.UserEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Android Studio.
 * User: KCJ
 * Date: 2019-07-04 18:42
 * Description:
 */
public enum DownLoadDao {
    INSTANCE;

    public long save(DownLoadEntity downLoadEntity) {
        return DBImpl.save(downLoadEntity);
    }

    public long delete(DownLoadEntity downLoadEntity) {
        return DBImpl.delete(downLoadEntity);
    }

    public long deleteAll() {
        return DBImpl.deleteAll(DownLoadEntity.class);
    }

    public List<DownLoadEntity> queryDownloadList() {
        ArrayList<DownLoadEntity> query = (ArrayList<DownLoadEntity>) DBImpl.query(DownLoadEntity.class);
        return query;
    }

    public DownLoadEntity queryUrlDownLoad(String url) {
        List<DownLoadEntity> list = queryDownloadList();
        for(DownLoadEntity dl:list){
            if(dl.getName().equals(url)){
                return dl;
            }
        }
        return null;
    }

    public long delDownLoad(String url) {
        List<DownLoadEntity> list = queryDownloadList();
        for(DownLoadEntity dl:list){
            if(dl.getUrl().equals(url)){
                return delete(dl);
            }
        }
        return 0;
    }
}
