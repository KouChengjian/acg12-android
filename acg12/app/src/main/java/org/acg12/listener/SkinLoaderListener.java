package org.acg12.listener;

import android.view.View;

import org.acg12.utlis.skin.entity.DynamicAttr;

import java.util.List;

/**
 * Created by DELL on 2017/1/16.
 */
public interface SkinLoaderListener {

    interface ISkinUpdate {
        void onThemeUpdate();

    }

    interface ISkinLoader {
        void attach(ISkinUpdate observer);
        void detach(ISkinUpdate observer);
        void notifySkinUpdate();
    }

    interface ILoaderListener {
        public void onStart();
        public void onSuccess();
        public void onFailed();
    }

    interface IDynamicNewView {
        void dynamicAddView(View view, List<DynamicAttr> pDAttrs);
    }

}
