package org.acg12.net.download;

import android.content.Context;


/**
 * Created by Administrator on 2017/5/24.
 */
public class DUtil {

    public static DownloadBuilder initDownloadBuilder(Context context) {
        return new DownloadBuilder(context);
    }

    public static class DownloadBuilder {
        private String url;//下载链接
        private String path;//保存路径
        private String name;//文件名
        private int childTaskCount;//单个任务采用几个线程下载

        private Context context;

        public DownloadBuilder(Context context) {
            this.context = context;
        }

        public DownloadBuilder() {

        }

        public DownloadBuilder url(String url) {
            this.url = url;
            return this;
        }

        public DownloadBuilder path(String path) {
            this.path = path;
            return this;
        }

        public DownloadBuilder name(String name) {
            this.name = name;
            return this;
        }

        public DownloadBuilder childTaskCount(int thread) {
            this.childTaskCount = thread;
            return this;
        }

        public DownloadManger build() {
            DownloadManger downloadManger = DownloadManger.getInstance(context);
            downloadManger.init(url, path, name, childTaskCount);
            return downloadManger;
        }
    }
}
