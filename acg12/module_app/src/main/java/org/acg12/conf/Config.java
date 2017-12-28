package org.acg12.conf;


import android.content.Context;

import com.acg12.common.conf.BaseConfig;
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;

/**
 * Created by DELL on 2016/11/25.
 */
public class Config extends BaseConfig{

    static ListVideoUtil listVideoUtil;

    static {
    }

    public Config(Context mContext){
        super(mContext);

    }

    // init in activity
    public static void initListVideoUtil(Context mContext){
        listVideoUtil = new ListVideoUtil(mContext);
        listVideoUtil.setHideActionBar(false);
        listVideoUtil.setHideStatusBar(false);
    }

    public static ListVideoUtil ListVideoUtilInstance(){
        return listVideoUtil;
    }

}
