package com.acg12.common.ui.base;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.acg12.common.dao.DaoBaseImpl;
import com.acg12.common.entity.User;
import com.acg12.common.utils.skin.SkinInflaterFactory;
import com.acg12.common.utils.skin.SkinManager;
import com.acg12.common.utils.skin.entity.DynamicAttr;
import com.acg12.common.utils.skin.listener.IDynamicNewView;
import com.acg12.common.utils.skin.listener.ISkinUpdate;
import com.acg12.lib.ui.IView;
import com.acg12.lib.ui.base.PresenterActivityImpl;

import java.util.List;

/**
 * Created by Administrator on 2017/12/13.
 */
public class BaseActivity<T extends IView> extends PresenterActivityImpl<T> implements ISkinUpdate, IDynamicNewView {

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
        if(!isResponseOnSkinChanging) return;
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

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /** --------------------skin 切换-------------------------*/
    private boolean isResponseOnSkinChanging = true;

    private SkinInflaterFactory mSkinInflaterFactory;

    public void initSkin(){
        mSkinInflaterFactory = new SkinInflaterFactory();
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this) , mSkinInflaterFactory);
    }

    protected void dynamicAddSkinEnableView(View view, String attrName, int attrValueResId){
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, attrName, attrValueResId);
    }

    protected void dynamicAddSkinEnableView(View view, List<DynamicAttr> pDAttrs){
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
    }

    final protected void enableResponseOnSkinChanging(boolean enable){
        isResponseOnSkinChanging = enable;
    }




}
