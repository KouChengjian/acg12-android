package org.acg12.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.skin.loader.listener.ILoaderListener;
import com.skin.loader.loader.SkinManager;
import com.skin.loader.utils.L;

import org.acg12.listener.ItemClickSupport;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.views.SkinLoaderView;

import java.io.File;

public class SkinLoaderActivity extends PresenterActivityImpl<SkinLoaderView> implements ItemClickSupport.OnItemClickListener {

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        boolean isOfficalSelected = !SkinManager.getInstance().isExternalSkin();
        if(isOfficalSelected){
            Log.e("官方默认(当前)","官方默认(当前)");
        }else{
            Log.e("黑色幻想","黑色幻想");
        }
    }

    private static final String SKIN_NAME = "BlackFantacy.skin";
    private static final String SKIN_DIR = Environment
            .getExternalStorageDirectory() + File.separator + SKIN_NAME;

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        File skin = new File(SKIN_DIR);
        if(skin == null){
            return;
        }
        SkinManager.getInstance().load(skin.getAbsolutePath(),
                new ILoaderListener() {
                    @Override
                    public void onStart() {
                        L.e("startloadSkin");
                    }

                    @Override
                    public void onSuccess() {
                        L.e("loadSkinSuccess");
                    }

                    @Override
                    public void onFailed() {
                        L.e("loadSkinFail");
                    }
                });
    }
}
