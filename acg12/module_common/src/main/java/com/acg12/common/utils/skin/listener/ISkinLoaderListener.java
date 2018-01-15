package com.acg12.common.utils.skin.listener;

import android.view.View;

import com.acg12.common.utils.skin.entity.DynamicAttr;

import java.util.List;

/**
 * Created by DELL on 2017/1/16.
 */
public interface ISkinLoaderListener {

    interface ISkinUpdate {
        void onThemeUpdate();

    }

    interface ISkinLoader {
        void attach(ISkinUpdate observer);
        void detach(ISkinUpdate observer);
        void notifySkinUpdate();
    }

    interface ILoaderListener {
        void onStart();
        void onSuccess();
        void onFailed();
    }

    interface IDynamicNewView {
        void dynamicAddView(View view, List<DynamicAttr> pDAttrs);
    }

}
