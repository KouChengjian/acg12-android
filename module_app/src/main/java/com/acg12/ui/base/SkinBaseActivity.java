package com.acg12.ui.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.acg12.cache.DaoBaseImpl;
import com.acg12.entity.User;

import com.acg12.cache.DaoBaseImpl;
import com.acg12.entity.User;
import com.acg12.lib.ui.base.IView;
import com.acg12.lib.ui.activity.PresenterActivityImpl;
import com.acg12.lib.utils.skin.SkinInflaterFactory;
import com.acg12.lib.utils.skin.SkinManager;
import com.acg12.lib.utils.skin.entity.DynamicAttr;
import com.acg12.lib.utils.skin.listener.IDynamicNewView;
import com.acg12.lib.utils.skin.listener.ISkinUpdate;

import java.util.List;

/**
 * Created by Administrator on 2017/12/13.
 */
public class SkinBaseActivity<T extends IView> extends PresenterActivityImpl<T> implements ISkinUpdate, IDynamicNewView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initSkin();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SkinManager.getInstance().attach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().detach(this);
    }

    @Override
    public void onThemeUpdate() {
        if (!isResponseOnSkinChanging) return;
        mSkinInflaterFactory.applySkin();
    }

    @Override
    public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
        SkinManager.getInstance().load();
    }

    // 用于接口请求获取用户，不能用于判断是否登入
    public User currentUser() {
        User user = DaoBaseImpl.getInstance(this).getCurrentUser();
        if (user == null) {
            return new User(mContext);
        } else {
            return user;
        }
    }

    public void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (true) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    /**
     * --------------------skin 切换-------------------------
     */
    private boolean isResponseOnSkinChanging = true;

    private SkinInflaterFactory mSkinInflaterFactory;

    public void initSkin() {
        mSkinInflaterFactory = new SkinInflaterFactory();
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), mSkinInflaterFactory);
    }

    protected void dynamicAddSkinEnableView(View view, String attrName, int attrValueResId) {
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, attrName, attrValueResId);
    }

    protected void dynamicAddSkinEnableView(View view, List<DynamicAttr> pDAttrs) {
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
    }

    final protected void enableResponseOnSkinChanging(boolean enable) {
        isResponseOnSkinChanging = enable;
    }


}
