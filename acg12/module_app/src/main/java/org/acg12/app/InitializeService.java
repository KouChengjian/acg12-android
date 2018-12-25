package org.acg12.app;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.acg12.lib.utils.PreferencesUtils;
import com.acg12.lib.utils.Toastor;
import com.acg12.lib.utils.glide.ImageLoadUtils;

import org.acg12.net.impl.HttpRequestImpl;
import org.acg12.utlis.cache.Cache;


public class InitializeService extends IntentService {

    private static Context mContext;

    public InitializeService() {
        super("InitializeService");
    }

    /**
     * 启动调用
     *
     * @param context
     */
    public static void start(Context context) {
        mContext = context;
        Intent intent = new Intent(context, InitializeService.class);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        initSdk();
    }

    private void initSdk() {
    }
}
