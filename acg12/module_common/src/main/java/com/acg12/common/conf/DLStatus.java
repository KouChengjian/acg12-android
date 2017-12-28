package com.acg12.common.conf;

/**
 * Created by Administrator on 2017/12/25.
 */
public class DLStatus {

    // 下载
    public static final int NONE = 1000; //无状态
    public static final int START = 1001; //准备下载
    public static final int PROGRESS = 1002; //下载中
    public static final int PAUSE = 1003; //暂停
    public static final int RESUME = 1004; //继续下载
    public static final int CANCEL = 1005; //取消
    public static final int RESTART = 1006; //重新下载
    public static final int FINISH = 1007; //下载完成
    public static final int ERROR = 1008; //下载出错
    public static final int WAIT = 1009; //等待中
    public static final int DESTROY = 1010; //释放资源
}
