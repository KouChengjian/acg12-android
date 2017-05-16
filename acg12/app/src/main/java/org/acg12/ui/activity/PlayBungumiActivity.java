package org.acg12.ui.activity;

import android.os.Bundle;
import android.view.WindowManager;

import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.ui.views.PlayBungumiView;

public class PlayBungumiActivity extends  PresenterActivityImpl<PlayBungumiView> {

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        replace = false;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
